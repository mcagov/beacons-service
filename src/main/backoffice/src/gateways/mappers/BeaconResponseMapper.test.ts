import * as _ from "lodash";
import { IBeacon } from "../../entities/IBeacon";
import { beaconFixture } from "../../fixtures/beacons.fixture";
import { singleBeaconApiResponseFixture } from "../../fixtures/singleBeaconApiResponse.fixture";
import { BeaconResponseMapper } from "./BeaconResponseMapper";
import { IBeaconResponse } from "./IBeaconResponse";

describe("BeaconResponseMapper", () => {
  let beaconApiResponse: IBeaconResponse;
  let expectedBeacon: IBeacon;

  beforeEach(() => {
    beaconApiResponse = _.cloneDeep(singleBeaconApiResponseFixture);
    expectedBeacon = _.cloneDeep(beaconFixture);
  });

  it("maps a single beacon API response payload to an IBeacon", () => {
    const responseMapper = new BeaconResponseMapper();

    const mappedBeacon = responseMapper.map(beaconApiResponse);

    expect(mappedBeacon).toStrictEqual(expectedBeacon);
  });

  it("maps a different single beacon API response payload to an IBeacon", () => {
    beaconApiResponse.data.attributes.model = "EPIRB2";
    expectedBeacon.model = "EPIRB2";
    const responseMapper = new BeaconResponseMapper();

    const mappedBeacon = responseMapper.map(beaconApiResponse);

    expect(mappedBeacon).toStrictEqual(expectedBeacon);
  });

  it("replaces undefined values with empty strings", () => {
    beaconApiResponse.data.attributes.batteryExpiryDate = undefined;
    beaconApiResponse.data.attributes.protocol = undefined;
    expectedBeacon.batteryExpiryDate = "";
    expectedBeacon.protocol = "";
    const responseMapper = new BeaconResponseMapper();

    const mappedBeacon = responseMapper.map(beaconApiResponse);

    expect(mappedBeacon).toStrictEqual(expectedBeacon);
  });
});
