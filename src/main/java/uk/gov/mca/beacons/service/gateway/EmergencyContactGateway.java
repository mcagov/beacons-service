package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.service.db.Person;

public interface EmergencyContactGateway {
    void save(CreateEmergencyContactRequest request);

    List<Person> findAllByBeaconId(UUID beaconId);
}
