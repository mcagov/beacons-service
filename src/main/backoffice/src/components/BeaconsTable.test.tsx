import { render, screen, waitFor } from "@testing-library/react";
import { IBeaconsGateway } from "gateways/beacons/IBeaconsGateway";
import { beaconSearchResultFixture } from "../fixtures/beaconSearchResult.fixture";
import { BeaconsTable } from "./BeaconsTable";

describe("<BeaconsTable>", () => {
  let beaconsGatewayDouble: IBeaconsGateway;

  beforeEach(() => {
    beaconsGatewayDouble = {
      getAllBeacons: jest.fn().mockResolvedValue(beaconSearchResultFixture),
      getBeacon: jest.fn(),
      getLegacyBeacon: jest.fn(),
      updateBeacon: jest.fn(),
    };
  });

  const numberOfMockedBeacons = beaconSearchResultFixture.page.totalElements;

  it("renders a table", async () => {
    render(<BeaconsTable beaconsGateway={beaconsGatewayDouble} />);

    expect((await screen.findAllByRole("table")).length).toBeGreaterThan(0);
  });

  it("queries the injected gateway for beacon data", async () => {
    render(<BeaconsTable beaconsGateway={beaconsGatewayDouble} />);

    await waitFor(() => {
      expect(beaconsGatewayDouble.getAllBeacons).toHaveBeenCalled();
    });
  });

  it("displays the returned beacon data in the table", async () => {
    render(<BeaconsTable beaconsGateway={beaconsGatewayDouble} />);

    expect(await screen.findByText("Hex me difficultly")).toBeVisible();
  });

  it(`displays ${numberOfMockedBeacons} rows when ${numberOfMockedBeacons} beacons are returned from the gateway`, async () => {
    render(<BeaconsTable beaconsGateway={beaconsGatewayDouble} />);

    expect(await screen.findAllByTestId("beacons-table-row")).toHaveLength(
      numberOfMockedBeacons
    );
  });

  // it("can click on the hex ID to see more details about the beacon", async () => {
  //   render(<BeaconsTable beaconsGateway={beaconsGatewayDouble} />);

  //   const hexIdField = await screen.findByText("Hex me");

  //   expect(hexIdField.getAttribute("href")).toBe(
  //     "/#/beacons/97b306aa-cbd0-4f09-aa24-2d876b983efb"
  //   );
  // });
});
