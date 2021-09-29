package uk.gov.mca.beacons.api.domain.events;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

@Getter
@Setter
@SuperBuilder
public class LegacyBeaconClaimEvent extends LegacyBeaconEvent {

  private UUID accountHolderId;

  public LegacyBeaconClaimEvent(
    UUID id,
    OffsetDateTime whenHappened,
    LegacyBeacon legacyBeacon,
    UUID accountHolderId
  ) {
    super(id, whenHappened, legacyBeacon);
    this.accountHolderId = accountHolderId;
  }
}
