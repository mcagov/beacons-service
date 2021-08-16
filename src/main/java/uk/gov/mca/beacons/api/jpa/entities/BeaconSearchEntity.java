package uk.gov.mca.beacons.api.jpa.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity(name = "beacon_search")
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
