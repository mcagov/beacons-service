package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

public interface BeaconGateway {
  List<Beacon> findAllByAccountHolderId(UUID accountId);

  List<Beacon> findAllActiveBeaconsByAccountHolderId(UUID accountId);

  void delete(UUID beaconId);
}
