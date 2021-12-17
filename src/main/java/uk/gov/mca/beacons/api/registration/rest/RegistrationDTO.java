package uk.gov.mca.beacons.api.registration.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import lombok.*;
import uk.gov.mca.beacons.api.beacon.rest.BeaconDTO;
import uk.gov.mca.beacons.api.beaconowner.rest.BeaconOwnerDTO;
import uk.gov.mca.beacons.api.beaconuse.rest.BeaconUseDTO;
import uk.gov.mca.beacons.api.emergencycontact.rest.EmergencyContactDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {

  @JsonUnwrapped
  BeaconDTO beaconDTO;

  @JsonProperty("owner")
  BeaconOwnerDTO beaconOwnerDTO;

  @JsonProperty("uses")
  List<BeaconUseDTO> beaconUseDTOs;

  @JsonProperty("emergencyContacts")
  List<EmergencyContactDTO> emergencyContactDTOs;
}
