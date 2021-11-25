package uk.gov.mca.beacons.api.shared.domain.base;

import java.time.Instant;
import org.springframework.lang.NonNull;

/**
 * This is a marker interface for all domain events.
 */
public interface DomainEvent {
  @NonNull
  Instant occurredOn();
}
