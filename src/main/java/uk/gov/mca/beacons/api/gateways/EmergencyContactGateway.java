package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.jpa.entities.Person;

public interface EmergencyContactGateway {
    void save(CreateEmergencyContactRequest request);

    List<Person> findAllByBeaconId(UUID beaconId);
}
