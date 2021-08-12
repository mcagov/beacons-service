package uk.gov.mca.beacons.api.jpa.entities;

import java.time.LocalDateTime;
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

  private LocalDateTime lastModifiedDate;
  private String beaconStatus;
  private String hexId;
  private String ownerName;
}
