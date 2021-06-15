package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateEmergencyContactRequest {

  private String fullName;

  private UUID beaconId;

  private String telephoneNumber;

  private String alternativeTelephoneNumber;

  private String email;

  private String addressLine1;

  private String addressLine2;

  private String addressLine3;

  private String addressLine4;

  private String townOrCity;

  private String postcode;

  private String county;
}
