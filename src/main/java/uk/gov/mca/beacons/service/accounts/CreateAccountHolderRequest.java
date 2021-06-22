package uk.gov.mca.beacons.service.accounts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountHolderRequest {

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
}
