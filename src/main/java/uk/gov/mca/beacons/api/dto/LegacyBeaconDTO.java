package uk.gov.mca.beacons.api.dto;

import static uk.gov.mca.beacons.api.dto.LegacyBeaconDTO.Attributes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Map<String, Object>> owners;
    private List<Map<String, Object>> uses;
    private Map<String, Object> emergencyContact;
  }
}
