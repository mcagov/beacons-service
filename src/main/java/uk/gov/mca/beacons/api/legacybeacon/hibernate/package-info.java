@TypeDef(
  defaultForType = LegacyBeaconId.class,
  typeClass = LegacyBeaconIdType.class
)
@GenericGenerator(
  name = LegacyBeacon.ID_GENERATOR_NAME,
  strategy = LegacyBeaconIdGenerator.STRATEGY
)
package uk.gov.mca.beacons.api.legacybeacon.hibernate;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import uk.gov.mca.beacons.api.legacybeacon.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.legacybeacon.domain.LegacyBeaconId;
