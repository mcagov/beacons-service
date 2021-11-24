package uk.gov.mca.beacons.api.shared.domain.base;

import javax.persistence.MappedSuperclass;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Reimplement
 */
@MappedSuperclass
public abstract class BaseEntity<Id extends DomainObjectId>
  extends AbstractPersistable<Id> {}
