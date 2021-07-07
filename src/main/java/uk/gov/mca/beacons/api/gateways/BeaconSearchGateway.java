package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;

public interface BeaconSearchGateway<T> {
  List<T> findAllByBeaconId(UUID beaconId);
}
