package uk.gov.mca.beacons.service.mappers;

import java.util.List;

import org.springframework.stereotype.Service;

import uk.gov.mca.beacons.service.dto.DomainDTO;
import uk.gov.mca.beacons.service.dto.RelationshipDTO;

@Service
public class RelationshipMapper<T extends DomainDTO>  {

  public RelationshipDTO<T> toDTO(T related) {
    final var relationship = new RelationshipDTO<T>();
    relationship.addData(related);
    return relationship;
  }

  public RelationshipDTO<T> toDTO(List<T> related) {
    final var relationship = new RelationshipDTO<T>();
    related.forEach(relationship::addData);

    return relationship;
  }
}
