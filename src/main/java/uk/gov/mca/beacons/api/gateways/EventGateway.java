package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;

public interface EventGateway {
  void save(LegacyBeaconClaimEvent event);
}
