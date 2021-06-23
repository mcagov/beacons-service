package uk.gov.mca.beacons.service.dto;

import static uk.gov.mca.beacons.service.dto.AccountHolderDTO.Attributes;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AccountHolderDTO extends DomainDTO<Attributes> {

  private String type = "accountHolder";

  public String getType() {
    return type;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Attributes {

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
}
