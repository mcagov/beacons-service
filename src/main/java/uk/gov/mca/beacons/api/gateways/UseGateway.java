package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.api.db.BeaconUse;

public interface UseGateway {
  List<BeaconUse> findAllByBeaconId(UUID beaconId);
}
