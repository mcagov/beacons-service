package uk.gov.mca.beacons.service.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class DomainDTO {

  private UUID id;
  private Map<String, Object> attributes = new HashMap<String, Object>();
  private Map<String, RelationshipDTO> relationships = new HashMap<String, RelationshipDTO>();

  public abstract String getType();

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

  public Map<String, RelationshipDTO> getRelationships() {
    return relationships;
  }

  public void AddRelationship(String key, RelationshipDTO value) {
    relationships.put(key, value);
  }
}
