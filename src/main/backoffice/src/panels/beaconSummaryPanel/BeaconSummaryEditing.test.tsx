import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { beaconFixture } from "../../fixtures/beacons.fixture";
import { BeaconSummaryEditing } from "./BeaconSummaryEditing";

describe("BeaconSummaryEditing", () => {
  it("allows user to edit basic string input fields", async () => {
    render(
      <BeaconSummaryEditing
        beacon={beaconFixture}
        onSave={jest.fn()}
        onCancel={jest.fn()}
      />
    );

    expect(
      await screen.findByDisplayValue(beaconFixture.manufacturer as string)
    ).toBeVisible();
    expect(
      await screen.findByDisplayValue(beaconFixture.model as string)
    ).toBeVisible();
    expect(
      await screen.findByDisplayValue(
        beaconFixture.manufacturerSerialNumber as string
      )
    ).toBeVisible();
    expect(
      await screen.findByDisplayValue(beaconFixture.chkCode as string)
    ).toBeVisible();
  });

  it("user can type text in basic string input fields", async () => {
    const onSave = jest.fn();

    render(
      <BeaconSummaryEditing
        beacon={beaconFixture}
        onSave={onSave}
        onCancel={jest.fn()}
      />
    );
    const editableField = await screen.findByDisplayValue(
      beaconFixture.manufacturer as string
    );

    userEvent.clear(editableField);
    userEvent.type(editableField, "ACME Inc.");

    expect(await screen.findByDisplayValue("ACME Inc.")).toBeVisible();
    userEvent.click(screen.getByRole("button", { name: "Save"}));
    await waitFor(() => {
      expect(onSave).toHaveBeenCalledWith(expect.objectContaining({ manufacturer: "ACME Inc." }))
    });
  });

  it("user can select item from dropdown fields", async () => {
    const onSave = jest.fn();

    render(
      <BeaconSummaryEditing
        beacon={beaconFixture}
        onSave={onSave}
        onCancel={jest.fn()}
      />
    );

    const dropdownField = await screen.findByLabelText(/mti/i);

    userEvent.selectOptions(dropdownField, "TEST_EPIRB");
    userEvent.click(screen.getByRole("button", { name: "Save"}));
    await waitFor(() => {
      expect(onSave).toHaveBeenCalledWith(expect.objectContaining({ mti: "TEST_EPIRB" }))
    });
  })

  it("calls the onSave() callback to save the edited beacon", async () => {
    const onSave = jest.fn();
    render(
      <BeaconSummaryEditing
        beacon={beaconFixture}
        onSave={onSave}
        onCancel={jest.fn()}
      />
    );
    const editableField = await screen.findByDisplayValue(
      beaconFixture.manufacturer as string
    );
    userEvent.clear(editableField);
    userEvent.type(editableField, "ACME Inc.");
    const saveButton = screen.getByRole("button", { name: "Save" });

    userEvent.click(saveButton);

    await waitFor(() => {
      expect(onSave).toHaveBeenCalledWith({
        ...beaconFixture,
        manufacturer: "ACME Inc.",
      });
    });
  });

  it("calls the cancel callback to abort the edit", async () => {
    const onCancel = jest.fn();
    render(
      <BeaconSummaryEditing
        beacon={beaconFixture}
        onSave={jest.fn()}
        onCancel={onCancel}
      />
    );
    const editableField = await screen.findByDisplayValue(
      beaconFixture.manufacturer as string
    );
    userEvent.clear(editableField);
    userEvent.type(editableField, "ACME Inc.");
    const cancelButton = screen.getByRole("button", { name: "Cancel" });

    userEvent.click(cancelButton);

    await waitFor(() => {
      expect(onCancel).toHaveBeenCalled();
    });
  });
});
