package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;
import uk.gov.mca.beacons.api.jpa.entities.Person;

public interface OwnerGateway {
  Person save(CreateOwnerRequest request);

  Person findByBeaconId(UUID beaconId);
}
