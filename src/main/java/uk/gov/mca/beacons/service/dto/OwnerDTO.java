package uk.gov.mca.beacons.service.dto;

import static uk.gov.mca.beacons.service.dto.OwnerDTO.Attributes;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OwnerDTO extends DomainDTO<Attributes> {

  private final String type = "owner";

  public String getType() {
    return type;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Attributes {

    private String fullName;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String telephoneNumber;
    private String telephoneNumber2;
    private String alternativeTelephoneNumber;
    private String alternativeTelephoneNumber2;
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
}
