package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateOwnerRequest {

  public CreateOwnerRequest(BeaconPerson owner, UUID beaconId) {
    this.fullName = owner.getFullName();
    this.beaconId = beaconId;
    this.telephoneNumber = owner.getTelephoneNumber();
    this.alternativeTelephoneNumber = owner.getAlternativeTelephoneNumber();
    this.email = owner.getEmail();
    this.addressLine1 = owner.getAddressLine1();
    this.addressLine2 = owner.getAddressLine2();
    this.addressLine3 = owner.getAddressLine3();
    this.addressLine4 = owner.getAddressLine4();
    this.townOrCity = owner.getTownOrCity();
    this.postcode = owner.getPostcode();
    this.county = owner.getCounty();
  }

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
