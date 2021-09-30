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
public abstract class LegacyBeaconEvent extends Event {

  protected LegacyBeacon legacyBeacon;

  public LegacyBeaconEvent(
    UUID id,
    OffsetDateTime whenHappened,
    LegacyBeacon legacyBeacon
  ) {
    super(id, whenHappened);
    this.legacyBeacon = legacyBeacon;
  }
}
