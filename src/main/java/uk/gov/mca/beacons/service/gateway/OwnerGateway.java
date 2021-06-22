package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import uk.gov.mca.beacons.service.db.Person;

public interface OwnerGateway {
    void save(CreateOwnerRequest request);

    Person findByBeaconId(UUID beaconId);
}
