package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.service.model.Beacon;

public interface BeaconGateway {
  List<Beacon> findAllByAccountHolderId(UUID accountId);
}
