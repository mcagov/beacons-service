package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.service.db.BeaconUse;

public interface UseGateway {
    List<BeaconUse> findAllByBeaconId(UUID beaconId);
}
