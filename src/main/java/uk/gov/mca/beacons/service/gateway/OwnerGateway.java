package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import uk.gov.mca.beacons.service.model.BeaconPerson;

public interface OwnerGateway {
  void save(CreateOwnerRequest request);

  BeaconPerson findByBeaconId(UUID beaconId);
}
