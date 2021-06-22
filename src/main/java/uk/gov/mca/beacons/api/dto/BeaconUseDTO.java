package uk.gov.mca.beacons.api.dto;

import java.util.HashMap;
import java.util.Map;

public class BeaconUseDTO extends DomainDTO<Map<String, Object>> {

  public BeaconUseDTO() {
    this.attributes = new HashMap<>();
  }

  private String type = "beaconUse";

  public String getType() {
    return type;
  }
}
