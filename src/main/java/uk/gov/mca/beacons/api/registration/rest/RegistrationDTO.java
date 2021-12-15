package uk.gov.mca.beacons.api.registration.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.beacon.rest.BeaconRegistrationDTO;
import uk.gov.mca.beacons.api.beaconowner.rest.BeaconOwnerRegistrationDTO;
import uk.gov.mca.beacons.api.beaconuse.rest.BeaconUseRegistrationDTO;
import uk.gov.mca.beacons.api.emergencycontact.rest.EmergencyContactRegistrationDTO;

@Getter
@Setter
public class RegistrationDTO {

  @JsonUnwrapped
  private BeaconRegistrationDTO beaconRegistrationDTO;

  @JsonProperty("uses")
  private List<BeaconUseRegistrationDTO> beaconUseRegistrationDTOs;

  @JsonProperty("owner")
  private BeaconOwnerRegistrationDTO beaconOwnerRegistrationDTO;

  @JsonProperty("emergencyContacts")
  private List<EmergencyContactRegistrationDTO> emergencyContactRegistrationDTOs;
}
