package uk.gov.mca.beacons.api.jpa.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "beacon_search_mat")
@Getter
@Setter
public class BeaconSearchEntity {

  @Id
  private UUID id;

  private LocalDateTime lastModifiedDate;
  private String beaconStatus;
  private String hexId;
  private String ownerName;
  private String ownerEmail;
  private UUID accountHolderId;
  private String useActivities;
}
