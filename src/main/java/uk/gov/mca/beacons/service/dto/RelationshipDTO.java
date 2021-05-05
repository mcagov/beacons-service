package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelationshipDTO<T extends DomainDTO> {

  private ArrayList<RelationshipData> data = new ArrayList<RelationshipData>();

  public static <T extends DomainDTO> RelationshipDTO<T> from(T related) {
    final var relationship = new RelationshipDTO<T>();
    relationship.addData(related);
    return relationship;
  }

  public static <T extends DomainDTO> RelationshipDTO<T> from(List<T> related) {
    final var relationship = new RelationshipDTO<T>();
    related.forEach(relationship::addData);

    return relationship;
  }

  public ArrayList<RelationshipData> getData() {
    return data;
  }

  public void addData(DomainDTO domainDto) {
    data.add(new RelationshipData(domainDto.getType(), domainDto.getId()));
  }

  public class RelationshipData {

    public String type;
    public UUID id;

    public RelationshipData(String type, UUID id) {
      this.type = type;
      this.id = id;
    }
  }
}
