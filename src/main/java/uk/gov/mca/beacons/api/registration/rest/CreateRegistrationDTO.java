package uk.gov.mca.beacons.api.registration.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import lombok.*;
import uk.gov.mca.beacons.api.beacon.rest.CreateBeaconDTO;
import uk.gov.mca.beacons.api.beaconowner.rest.CreateBeaconOwnerDTO;
import uk.gov.mca.beacons.api.beaconuse.rest.CreateBeaconUseDTO;
import uk.gov.mca.beacons.api.emergencycontact.rest.CreateEmergencyContactDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegistrationDTO {

  @JsonUnwrapped
  private CreateBeaconDTO beaconRegistrationDTO;

  @JsonProperty("uses")
  private List<CreateBeaconUseDTO> beaconUseRegistrationDTOs;

  @JsonProperty("owner")
  private CreateBeaconOwnerDTO beaconOwnerRegistrationDTO;

  @JsonProperty("emergencyContacts")
  private List<CreateEmergencyContactDTO> emergencyContactRegistrationDTOs;
}
