package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@Getter
@Setter
@Builder
public class CreateOwnerRequest {

  private UUID beaconId;
  private String fullName;
  private String telephoneNumber;
  private String telephoneNumber2;
  private String alternativeTelephoneNumber;
  private String alternativeTelephoneNumber2;
  private String email;
  private String addressLine1;
  private String addressLine2;
  private String addressLine3;
  private String addressLine4;
  private String townOrCity;
  private String postcode;
  private String county;
  private String country;
  private String companyName;
  private String careOf;
  private String fax;
  private String isMain;
  private Integer createUserId;
  private Integer updateUserId;
  private Integer versioning;
}
