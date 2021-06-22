package uk.gov.mca.beacons.api.dto;

import java.util.HashMap;
import java.util.Map;

public class BeaconPersonDTO extends DomainDTO<Map<String, Object>> {

  public BeaconPersonDTO() {
    this.attributes = new HashMap<>();
  }

  private String type = "beaconPerson";

  public String getType() {
    return type;
  }
}
