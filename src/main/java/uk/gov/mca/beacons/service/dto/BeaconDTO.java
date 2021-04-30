package uk.gov.mca.beacons.service.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BeaconDTO {

  private String type = "beacon";
  private UUID id;
  private Map<String, Object> attributes = new HashMap<String, Object>();

  public String getType() {
    return type;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void AddAttribute(String key, Object value) {
    attributes.put(key, value);
  }
}
