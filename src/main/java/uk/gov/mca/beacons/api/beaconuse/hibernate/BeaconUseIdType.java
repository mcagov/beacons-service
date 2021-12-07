package uk.gov.mca.beacons.api.beaconuse.hibernate;

import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwnerId;
import uk.gov.mca.beacons.api.shared.hibernate.DomainObjectIdCustomType;
import uk.gov.mca.beacons.api.shared.hibernate.DomainObjectIdTypeDescriptor;

public class BeaconUseIdType extends DomainObjectIdCustomType<BeaconOwnerId> {

  private static final DomainObjectIdTypeDescriptor<BeaconOwnerId> TYPE_DESCRIPTOR = new DomainObjectIdTypeDescriptor<>(
    BeaconOwnerId.class,
    BeaconOwnerId::new
  );

  public BeaconUseIdType() {
    super(TYPE_DESCRIPTOR);
  }
}
