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
import MaterialTable, { Icons, MTableBodyRow } from "material-table";
import React, { forwardRef, FunctionComponent, useState } from "react";
import { Placeholders } from "utils/writingStyle";
import { IBeaconSearchResultData } from "../entities/IBeaconSearchResult";

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
}

export const BeaconsTable: FunctionComponent<IBeaconsTableProps> = ({
  beaconsGateway,
}): JSX.Element => {
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
    SortArrow: forwardRef((props, ref) => (
      <ArrowDownward {...props} ref={ref} />
    )),
    ThirdStateCheck: forwardRef((props, ref) => (
      <Remove {...props} ref={ref} />
    )),
    ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />),
  };

  const [isLoading, setIsLoading] = useState(false);

  return (
    <MaterialTable
      icons={tableIcons}
      isLoading={isLoading}
      columns={[
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
                <Link href={"/#/legacy-beacons/" + rowData.id}>
                  {rowData.hexId ? rowData.hexId : <i>{Placeholders.NoData}</i>}
                </Link>
              );
            } else {
              return (
                <Link href={"/#/beacons/" + rowData.id}>
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
      ]}
      data={(query) =>
        new Promise(async (resolve, reject) => {
          setIsLoading(true);
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
              })
            );
            setIsLoading(false);
            resolve({
              data: beacons,
              page: response.page.number,
              totalCount: response.page.totalElements,
            });
          } catch (error) {
            console.error("Could not fetch beacons", error);
            setIsLoading(false);
          }
        })
      }
      title=""
      options={{
        filtering: true,
        search: true,
        searchFieldVariant: "outlined",
        debounceInterval: 2000,
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
};
