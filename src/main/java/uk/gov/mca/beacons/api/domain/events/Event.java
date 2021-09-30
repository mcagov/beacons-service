package uk.gov.mca.beacons.api.domain.events;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class Event {

  protected UUID id;
  protected OffsetDateTime whenHappened;

  protected Event(UUID id, OffsetDateTime whenHappened) {
    if (id == null) {
      this.id = UUID.randomUUID();
    } else {
      this.id = id;
    }

    this.whenHappened = whenHappened;
  }
}
