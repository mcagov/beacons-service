package uk.gov.mca.beacons.api.jpa.entities;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "beacon_search")
@Getter
@Setter
public class BeaconSearchEntity {

  @Id
  private UUID id;

  private String hexId;
}
