package uk.gov.mca.beacons.api.domain;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder implements User {

  private UUID id;

  private String authId;

  private String email;

  private String fullName;

  private String telephoneNumber;

  private String alternativeTelephoneNumber;

  private String addressLine1;

  private String addressLine2;

  private String addressLine3;

  private String addressLine4;

  private String townOrCity;

  private String postcode;

  private String county;

  private String country;
}
