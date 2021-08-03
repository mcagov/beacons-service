package uk.gov.mca.beacons.api.dto;

import static uk.gov.mca.beacons.api.dto.LegacyBeaconDTO.Attributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LegacyBeaconDTO extends DomainDTO<Attributes> {

  private String type = "legacyBeacon";

  public String getType() {
    return type;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Attributes {

    private Map<String, Object> beacon;
    private Map<String, Object> owner;
    private List<Map<String, Object>> secondaryOwners;
    private List<Map<String, Object>> uses;
    private Map<String, Object> emergencyContact;
  }
}
