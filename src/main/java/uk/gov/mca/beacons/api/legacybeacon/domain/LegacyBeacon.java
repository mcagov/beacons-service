package uk.gov.mca.beacons.api.legacybeacon.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
@Entity(name = "legacy_beaconV2")
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

  @OneToMany(
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.EAGER
  )
  @JoinColumn(name = "legacy_beacon_id", nullable = false)
  private List<LegacyBeaconAction> actions;

  @Setter
  @NotNull
  private OffsetDateTime createdDate;

  @Setter
  @NotNull
  private OffsetDateTime lastModifiedDate;

  public boolean isClaimed() {
    if (actions == null) {
      return false;
    }

    return actions.stream().anyMatch(LegacyBeaconClaimAction.class::isInstance);
  }

  public void claim() {
    initActions();
    if (!isClaimed()) {
      LegacyBeaconClaimAction claimAction = new LegacyBeaconClaimAction();
      actions.add(claimAction);
    }
  }

  // TODO: Add constructor to initialize list of actions
  private void initActions() {
    if (actions == null) {
      actions = new ArrayList<>();
    }
  }
}
