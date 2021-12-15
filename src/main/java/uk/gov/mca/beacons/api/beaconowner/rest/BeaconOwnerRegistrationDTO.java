package uk.gov.mca.beacons.api.beaconowner.rest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import uk.gov.mca.beacons.api.shared.rest.person.dto.AddressDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeaconOwnerRegistrationDTO {

  private String fullName;
  private String email;
  private String telephoneNumber;
  private String alternativeTelephoneNumber;

  @JsonUnwrapped
  private AddressDTO addressDTO;
}
