package uk.gov.mca.beacons.api.domain.events;

import java.time.OffsetDateTime;
import java.util.UUID;

public abstract class LegacyBeaconEvent extends Event {

  public LegacyBeaconEvent(UUID id, OffsetDateTime whenHappened) {
    super(id, whenHappened);
  }
}
