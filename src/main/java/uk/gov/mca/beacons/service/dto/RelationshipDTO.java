package uk.gov.mca.beacons.service.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RelationshipDTO {

  private ArrayList<RelationshipData> data = new ArrayList<RelationshipData>();

  public static RelationshipDTO from(List<DomainDTO> related) {
    var relationship = new RelationshipDTO();
    related.forEach(relationship::AddData);

    return relationship;
  }

  public ArrayList<RelationshipData> getData() {
    return data;
  }

  public void AddData(DomainDTO domainDto) {
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
