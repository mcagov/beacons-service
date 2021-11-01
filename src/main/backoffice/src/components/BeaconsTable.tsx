import { Chip, Link, Paper } from "@material-ui/core";
import {
  AddBox,
  ArrowDownward,
  Check,
  ChevronLeft,
  ChevronRight,
  Clear,
  DeleteOutline,
  Edit,
  FilterList,
  FirstPage,
  LastPage,
  Remove,
  SaveAlt,
  Search,
  ViewColumn,
} from "@material-ui/icons";
import { IBeaconsGateway } from "gateways/beacons/IBeaconsGateway";
import MaterialTable, {
  Column,
  Icons,
  MTableBodyRow,
} from "@material-table/core";
import React, { forwardRef, FunctionComponent, useState } from "react";
import { Link as RouterLink } from "react-router-dom";
import { Placeholders } from "utils/writingStyle";
import { IBeaconSearchResultData } from "../entities/IBeaconSearchResult";
import { replaceNone } from "../lib/legacyData/replaceNone";

interface IBeaconsTableProps {
  beaconsGateway: IBeaconsGateway;
}

interface BeaconTableListRow {
  hexId: string;
  ownerName: string;
  useActivities: string;
  id: string;
  lastModifiedDate: string;
  beaconStatus: string;
  beaconType: string;
  cospasSarsatNumber: string;
  manufacturerSerialNumber: string;
  serialNumber: string;
}

const tableIcons: Icons = {
  Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
  Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
  Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
  DetailPanel: forwardRef((props, ref) => (
    <ChevronRight {...props} ref={ref} />
  )),
  Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
  Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
  Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
  FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
  LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
  NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
  PreviousPage: forwardRef((props, ref) => (
    <ChevronLeft {...props} ref={ref} />
  )),
  ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
  Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
  SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref} />),
  ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
  ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />),
};

export const BeaconsTable: FunctionComponent<IBeaconsTableProps> = React.memo(
  function ({ beaconsGateway }): JSX.Element {
    const columns: Column<BeaconTableListRow>[] = React.useMemo(
      () => [
        {
          title: "Last modified date",
          field: "lastModifiedDate",
          filtering: false,
          defaultSort: "desc",
          type: "datetime",
          dateSetting: { format: "dd MM yyyy", locale: "en-GB" },
        },
        {
          title: "Status",
          field: "beaconStatus",
          lookup: { NEW: "NEW", MIGRATED: "MIGRATED", DELETED: "DELETED" },
          render: (rowData: BeaconTableListRow) => {
            if (rowData.beaconStatus === "MIGRATED") {
              return <Chip label={rowData.beaconStatus} color="secondary" />;
            } else {
              return <Chip label={rowData.beaconStatus} color="primary" />;
            }
          },
        },
        {
          title: "Hex ID",
          field: "hexId",
          filtering: false,
          render: (rowData: BeaconTableListRow) => {
            if (rowData.beaconType === "LEGACY_BEACON") {
              return (
                <Link
                  component={RouterLink}
                  to={"/legacy-beacons/" + rowData.id}
                >
                  {rowData.hexId ? rowData.hexId : <i>{Placeholders.NoData}</i>}
                </Link>
              );
            } else {
              return (
                <Link component={RouterLink} to={"/beacons/" + rowData.id}>
                  {rowData.hexId ? rowData.hexId : <i>{Placeholders.NoData}</i>}
                </Link>
              );
            }
          },
        },
        {
          title: "Owner details",
          field: "ownerName",
          filtering: false,
          render: (rowData: BeaconTableListRow) => {
            return rowData.ownerName ? rowData.ownerName.toUpperCase() : "";
          },
        },
        {
          title: "Beacon use",
          field: "useActivities",
          render: (rowData: BeaconTableListRow) => {
            return rowData.useActivities
              ? rowData.useActivities.toUpperCase()
              : "";
          },
        },
        {
          title: "Manufacturer Serial Number",
          field: "manufacturerSerialNumber",
          render: (rowData: BeaconTableListRow) =>
            replaceNone(rowData.manufacturerSerialNumber),
        },
        {
          title: "Cospas Sarsat Number",
          field: "cospasSarsatNumber",
          render: (rowData: BeaconTableListRow) =>
            replaceNone(rowData.cospasSarsatNumber),
        },
        {
          title: "Serial Number",
          field: "serialNumber",
          render: (rowData: BeaconTableListRow) =>
            replaceNone(rowData.serialNumber),
        },
      ],
      []
    );

    return (
      <MaterialTable
        icons={tableIcons}
        columns={columns}
        data={(query) =>
          new Promise(async (resolve, reject) => {
            const term = query.search;
            let statusFilterValue = "";
            let useFilterValue = "";
            let sortValue = "";
            query.filters.forEach((filter) => {
              if (filter.column.field === "beaconStatus")
                statusFilterValue = filter.value;
              if (filter.column.field === "useActivities")
                useFilterValue = filter.value;
            });
            if (query.orderBy) {
              const sortField = query.orderBy.field;
              const sortDirection = query.orderDirection;
              if (sortField && sortDirection) {
                sortValue = `${sortField},${sortDirection}`;
              }
            }
            try {
              const response = await beaconsGateway.getAllBeacons(
                term,
                statusFilterValue,
                useFilterValue,
                query.page,
                query.pageSize,
                sortValue
              );
              const beacons = response._embedded.beaconSearch.map(
                (item: IBeaconSearchResultData): BeaconTableListRow => ({
                  lastModifiedDate: item.lastModifiedDate,
                  beaconStatus: item.beaconStatus,
                  hexId: item.hexId,
                  ownerName: item.ownerName ?? "N/A",
                  useActivities: item.useActivities ?? "N/A",
                  id: item.id,
                  beaconType: item.beaconType,
                  cospasSarsatNumber: item.cospasSarsatNumber ?? "N/A",
                  manufacturerSerialNumber:
                    item.manufacturerSerialNumber ?? "N/A",
                  serialNumber: item.serialNumber ?? "N/A",
                })
              );
              resolve({
                data: beacons,
                page: response.page.number,
                totalCount: response.page.totalElements,
              });
            } catch (error) {
              console.error("Could not fetch beacons", error);
            }
          })
        }
        title=""
        options={{
          filtering: true,
          search: true,
          searchFieldVariant: "outlined",
          debounceInterval: 1000,
          pageSize: 20,
        }}
        components={{
          Container: (props) => <Paper {...props} elevation={0} />,
          Row: (props) => (
            <MTableBodyRow {...props} data-testid="beacons-table-row" />
          ),
        }}
      />
    );
  }
);
