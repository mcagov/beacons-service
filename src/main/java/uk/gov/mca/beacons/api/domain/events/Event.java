package uk.gov.mca.beacons.api.domain.events;

import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public abstract class Event {

  protected final OffsetDateTime dateTime;

  protected Event(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
  }
}
