package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import uk.gov.mca.beacons.service.hateoas.HateoasLink;

public abstract class DomainDTO<T> {

  private UUID id;
  protected T attributes;
  private final List<HateoasLink> links = new ArrayList<HateoasLink>();
  private final Map<String, RelationshipDTO> relationships = new HashMap<String, RelationshipDTO>();

  public abstract String getType();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public T getAttributes() {
    return attributes;
  }

  public void setAttributes(T attributes) {
    this.attributes = attributes;
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

  public void addRelationship(String key, RelationshipDTO value) {
    relationships.put(key, value);
  }
}
