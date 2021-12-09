package uk.gov.mca.beacons.api.legacybeacon.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import java.time.OffsetDateTime;
import java.util.Map;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uk.gov.mca.beacons.api.beacon.domain.BeaconId;
import uk.gov.mca.beacons.api.shared.domain.base.BaseAggregateRoot;
import uk.gov.mca.beacons.api.shared.domain.person.Address;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "legacy_beacon2.0")
@Table(name = "legacy_beacon")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) })
public class LegacyBeacon extends BaseAggregateRoot<LegacyBeaconId> {

  public static final String ID_GENERATOR_NAME = "legacybeacon-id-generator";

  @Type(type = "uk.gov.mca.beacons.api.legacybeacon.domain.LegacyBeaconId")
  @Column(nullable = false)
  @Id
  @GeneratedValue(
    strategy = GenerationType.AUTO,
    generator = "legacybeacon-id-generator"
  )
  private LegacyBeaconId id;

  @Setter
  private String hexId;

  @Setter
  private String ownerEmail;

  @Setter
  private String beaconStatus;

  @Setter
  private String ownerName;

  @Setter
  private String useActivities;

  @Setter
  @Type(type = "json")
  @Column(columnDefinition = "jsonb")
  private LegacyData data;

  @Setter
  @NotNull
  private OffsetDateTime createdDate;

  @Setter
  @NotNull
  private OffsetDateTime lastModifiedDate;
}
