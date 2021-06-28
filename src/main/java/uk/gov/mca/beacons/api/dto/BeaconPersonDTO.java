package uk.gov.mca.beacons.api.dto;

import static uk.gov.mca.beacons.api.dto.BeaconPersonDTO.Attributes;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BeaconPersonDTO extends DomainDTO<Attributes> {

  private String type = "beaconPerson";

  public String getType() {
    return type;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Attributes {

    @NotNull(message = "Full name must not be null")
    private String fullName;

    @NotNull(message = "Email must not be null")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    @NotNull(message = "Telephone number must not be null")
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
