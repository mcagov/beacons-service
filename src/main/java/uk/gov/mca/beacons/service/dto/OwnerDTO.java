package uk.gov.mca.beacons.service.dto;

import static uk.gov.mca.beacons.service.dto.OwnerDTO.Attributes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class OwnerDTO extends DomainDTO<Attributes> {

  private String type = "owner";

  public String getType() {
    return type;
  }

  @Data
  public static class Attributes {

    private String fullName;
  }
}
