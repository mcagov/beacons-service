package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

public interface LegacyBeaconGateway {
  LegacyBeacon save(LegacyBeacon beacon);

  void deleteAll();

  Optional<LegacyBeacon> findById(UUID id);

  List<LegacyBeacon> findAllByHexIdAndEmail(String hexId, String email);
}
