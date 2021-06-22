package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;

public interface EmergencyContactGateway {
    void save(CreateEmergencyContactRequest request);

    List<Person> findAllByBeaconId(UUID beaconId);
}
