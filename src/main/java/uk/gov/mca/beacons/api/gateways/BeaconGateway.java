package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

public interface BeaconGateway {
  Optional<Beacon> findById(UUID id);

  Beacon update(Beacon beacon);

  List<Beacon> findAllActiveBeaconsByAccountHolderId(UUID accountId);

  void delete(UUID id);
}
