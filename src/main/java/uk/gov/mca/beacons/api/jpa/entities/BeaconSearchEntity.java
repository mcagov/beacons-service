package uk.gov.mca.beacons.api.jpa.entities;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "beacon_search")
public class BeaconSearchEntity {

  @Id
  private UUID id;
}
