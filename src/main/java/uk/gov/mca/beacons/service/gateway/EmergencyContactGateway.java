package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.service.model.BeaconPerson;

public interface EmergencyContactGateway {
  void save(CreateEmergencyContactRequest request);

  List<BeaconPerson> findAllByBeaconId(UUID beaconId);
}
