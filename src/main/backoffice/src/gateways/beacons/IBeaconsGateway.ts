import { IBeaconSearchResult } from "entities/IBeaconSearchResult";
import { ILegacyBeacon } from "entities/ILegacyBeacon";
import { IBeacon } from "../../entities/IBeacon";

export interface IBeaconsGateway {
  getAllBeacons: (
    term: string,
    status: string,
    uses: string,
    page: number,
    pageSize: number,
    sort: string
  ) => Promise<IBeaconSearchResult>;
  getBeacon: (id: string) => Promise<IBeacon>;
  getLegacyBeacon: (id: string) => Promise<ILegacyBeacon>;
  updateBeacon: (
    beaconId: string,
    updatedFields: Partial<IBeacon>
  ) => Promise<IBeacon>;
}
