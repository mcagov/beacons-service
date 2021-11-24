package uk.gov.mca.beacons.api.shared.rest.person.dto;

import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

  @NotNull(message = "Address Line 1 must not be null")
  private String addressLine1;

  private String addressLine2;
  private String addressLine3;
  private String addressLine4;
  private String townOrCity;
  private String postcode;
  private String county;
  private String country;
}
