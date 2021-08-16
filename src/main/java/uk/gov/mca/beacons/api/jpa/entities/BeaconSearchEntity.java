package uk.gov.mca.beacons.api.jpa.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "beacon_search")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Getter
@Setter
public class BeaconSearchEntity {

  @Id
  private UUID id;

  private LocalDateTime lastModifiedDate;
  private String beaconStatus;
  private String hexId;
  private String ownerName;
  private String useActivities;
}
