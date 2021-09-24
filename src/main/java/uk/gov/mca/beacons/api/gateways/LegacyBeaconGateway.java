package uk.gov.mca.beacons.api.gateways;

import java.util.Optional;
import java.util.UUID;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

public interface LegacyBeaconGateway {
  LegacyBeacon save(LegacyBeacon beacon);

  void delete(UUID id);

  void deleteAll();

  Optional<LegacyBeacon> findById(UUID id);
  // Optional<LegacyBeacon> betterFindById(UUID id);
}
