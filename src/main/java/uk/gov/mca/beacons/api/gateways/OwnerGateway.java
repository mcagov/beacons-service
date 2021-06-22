package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;

public interface OwnerGateway {
    void save(CreateOwnerRequest request);

    Person findByBeaconId(UUID beaconId);
}
