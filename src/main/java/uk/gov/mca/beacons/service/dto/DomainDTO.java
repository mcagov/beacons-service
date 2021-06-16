package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import uk.gov.mca.beacons.service.hateoas.HateoasLink;

public abstract class DomainDTO {

  private UUID id;
  private final Map<String, Object> attributes = new HashMap<>();
  private final List<HateoasLink> links = new ArrayList<>();
  private final Map<String, RelationshipDTO> relationships = new HashMap<>();

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

  public void addAttribute(String key, Object value) {
    attributes.put(key, value);
  }

  public List<HateoasLink> getLinks() {
    return links;
  }

  public void addLinks(List<HateoasLink> addedLinks) {
    links.addAll(addedLinks);
  }

  public Map<String, RelationshipDTO> getRelationships() {
    return relationships;
  }

  public <T extends DomainDTO> void addRelationship(
    String key,
    RelationshipDTO value
  ) {
    relationships.put(key, value);
  }
}
