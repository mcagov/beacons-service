package uk.gov.mca.beacons.api.domain.events;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public abstract class Event {

  protected UUID id;
  protected OffsetDateTime dateTime;

  protected Event(UUID id, OffsetDateTime dateTime) {
    if (id == null) {
      this.id = UUID.randomUUID();
    } else {
      this.id = id;
    }

    this.dateTime = dateTime;
  }
}