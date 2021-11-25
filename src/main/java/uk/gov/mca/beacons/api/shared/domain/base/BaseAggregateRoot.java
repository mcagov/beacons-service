package uk.gov.mca.beacons.api.shared.domain.base;

import java.util.*;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

/**
 *
 * Reimplementation of {@link org.springframework.data.domain.AbstractAggregateRoot} but extends {@link BaseEntity}
 */
@MappedSuperclass
public abstract class BaseAggregateRoot<Id extends DomainObjectId>
  extends BaseEntity<Id> {

  @Transient
  private final List<Object> domainEvents = new ArrayList<>();

  protected void registerEvent(@NotNull Object event) {
    domainEvents.add(Objects.requireNonNull(event));
  }

  @AfterDomainEventPublication
  protected void clearDomainEvents() {
    this.domainEvents.clear();
  }

  @DomainEvents
  protected Collection<Object> domainEvents() {
    return Collections.unmodifiableList(domainEvents);
  }
}
