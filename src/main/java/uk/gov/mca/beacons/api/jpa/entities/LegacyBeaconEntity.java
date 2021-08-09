package uk.gov.mca.beacons.api.jpa.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import uk.gov.mca.beacons.api.domain.BeaconStatus;

@Entity(name = "legacy_beacon")
@Getter
@Setter
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) })
public class LegacyBeaconEntity {

  @Id
  @GeneratedValue
  private UUID id;

  @Type(type = "json")
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> data;

  private String hexId;

  private String ownerEmail;

  private LocalDateTime createdDate;

  private LocalDateTime lastModifiedDate;

  @Enumerated(EnumType.STRING)
  private BeaconStatus beaconStatus;
}
