package uk.gov.mca.beacons.api.jpa.entities;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "beacon_search_alt")
@Getter
@Setter
public class BeaconSearchEntityAlt {

  @Id
  private UUID id;

  private OffsetDateTime createdDate;
  private OffsetDateTime lastModifiedDate;
  private String beaconStatus;
  private String hexId;
  private String ownerName;
  private String ownerEmail;
  private UUID accountHolderId;
  private String useActivities;
}
