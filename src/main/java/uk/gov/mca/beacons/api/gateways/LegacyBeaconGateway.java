package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.domain.LegacyBeacon;

public interface LegacyBeaconGateway {
  LegacyBeacon save(LegacyBeacon beacon);
}
