package uk.gov.mca.beacons.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOwnerRequest {

  private UUID beaconId;
  private UUID accountHolderId;
  private String fullName;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
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
