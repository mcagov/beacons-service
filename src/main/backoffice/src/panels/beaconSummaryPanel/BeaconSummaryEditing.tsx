import {
  Box,
  Button,
  Divider,
  Grid,
  Input,
  Table,
  TableBody,
  TableContainer,
  Typography,
} from "@material-ui/core";
import { Field, Form, Formik, FormikHelpers } from "formik";
import React, { FunctionComponent } from "react";
import { PanelViewingState } from "../../components/dataPanel/PanelViewingState";
import { TabulatedRow } from "../../components/dataPanel/TabulatedRow";
import { BeaconTypes, IBeacon } from "../../entities/IBeacon";
import {
  formatEmergencyContacts,
  formatOwners,
  formatUses,
  Placeholders,
  WritingStyle,
} from "../../utils/writingStyle";

export const BeaconSummaryEditing: FunctionComponent<{
  beacon: IBeacon;
  onSave: (beacon: IBeacon) => void;
  onCancel: () => void;
}> = ({ beacon, onSave, onCancel }) => {
  return (
    <Formik
      initialValues={beacon}
      onSubmit={(
        values: IBeacon,
        { setSubmitting }: FormikHelpers<IBeacon>
      ) => {
        onSave(values);
        setSubmitting(false);
      }}
    >
      {() => (
        <Form>
          <Grid container direction="row" justifyContent={"flex-start"}>
            <Grid item xs={12} sm={6}>
              <TableContainer>
                <Table size="small">
                  <TableBody>
                    <TabulatedRow
                      displayKey={
                        <label htmlFor="manufacturer">
                          <Typography>
                            {"Manufacturer" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="manufacturer"
                          name="manufacturer"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />
                    <TabulatedRow
                      displayKey={
                        <label htmlFor="model">
                          <Typography>
                            {"Model" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="model"
                          name="model"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="manufacturerSerialNumber">
                          <Typography>
                            {"Serial number" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="manufacturerSerialNumber"
                          name="manufacturerSerialNumber"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="chkCode">
                          <Typography>
                            {"CHK code" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="chkCode"
                          name="chkCode"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="beaconType">
                          <Typography>
                            {"Beacon type" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field as="select" name="beaconType">
                          <option value="" label={Placeholders.NoData} />
                          {Object.values(BeaconTypes).map(
                            (beaconType: string) => {
                              return (
                                <option value={beaconType} label={beaconType} />
                              );
                            }
                          )}
                        </Field>
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <Typography>
                          {"Protocol" + WritingStyle.KeyValueSeparator}
                        </Typography>
                      }
                      value={
                        <Field
                          as={Input}
                          id="protocol"
                          name="protocol"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <Typography>
                          {"Coding" + WritingStyle.KeyValueSeparator}
                        </Typography>
                      }
                      value={
                        <Field
                          as={Input}
                          id="coding"
                          name="coding"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="csta">
                          <Typography>
                            {"CSTA" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="csta"
                          name="csta"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="mti">
                          <Typography>
                            {"MTI" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="mti"
                          name="mti"
                          type="string"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="svdr">
                          <Typography>
                            {"SVDR" + WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <>
                          <label>
                            <Field name="svdr" type="radio" value="true" />
                            Yes
                          </label>
                          <label>
                            <Field name="svdr" type="radio" value="false" />
                            No
                          </label>
                        </>
                      }
                    />

                    {/*<TabulatedRow*/}
                    {/*  displayKey={<></>}*/}
                    {/*  value={*/}
                    {/*    <Paper style={{ backgroundColor: "#FFFCC8" }}>*/}
                    {/*      <Box p={1}>*/}
                    {/*        <Typography>*/}
                    {/*          <b>Protocol code</b>, <b>coding method</b> and{" "}*/}
                    {/*          <b>beacon type</b> are automatically derived from*/}
                    {/*          the HEX ID. If you have identified a coding issue,*/}
                    {/*          please flag this record as 'Incorrectly Encoded'*/}
                    {/*          by clicking on the 'Quick Actions' button in the*/}
                    {/*          top right. You can also add any notes in the*/}
                    {/*          'Notes' tab below.*/}
                    {/*        </Typography>*/}
                    {/*      </Box>*/}
                    {/*    </Paper>*/}
                    {/*  }*/}
                    {/*/>*/}

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="batteryExpiryDate">
                          <Typography>
                            {"Battery expiry date" +
                              WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="batteryExpiryDate"
                          name="batteryExpiryDate"
                          type="date"
                          fullWidth
                          placeholder={Placeholders.NoData}
                        />
                      }
                    />

                    <TabulatedRow
                      displayKey={
                        <label htmlFor="lastServicedDate">
                          <Typography>
                            {"Last serviced date" +
                              WritingStyle.KeyValueSeparator}
                          </Typography>
                        </label>
                      }
                      value={
                        <Field
                          as={Input}
                          id="lastServicedDate"
                          name="lastServicedDate"
                          type="date"
                          fullWidth
                        />
                      }
                    />
                  </TableBody>
                </Table>
              </TableContainer>
            </Grid>
            <Grid item xs={12} sm={6}>
              <PanelViewingState
                columns={1}
                fields={[
                  {
                    key: "Owner(s)",
                    value: formatOwners(beacon.owners),
                  },
                  {
                    key: "Emergency contacts",
                    value: formatEmergencyContacts(beacon.emergencyContacts),
                  },
                  {
                    key: "Registered uses",
                    value: formatUses(beacon.uses),
                  },
                ]}
              />
            </Grid>
            <Divider />
            <Box mt={2} mr={2}>
              <Button
                name="save"
                type="submit"
                color="secondary"
                variant="contained"
                disableElevation
              >
                Save
              </Button>
            </Box>
            <Box mt={2}>
              <Button onClick={onCancel}>Cancel</Button>
            </Box>
          </Grid>
        </Form>
      )}
    </Formik>
  );
};
