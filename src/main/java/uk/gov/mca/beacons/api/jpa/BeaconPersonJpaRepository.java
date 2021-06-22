package uk.gov.mca.beacons.api.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.gov.mca.beacons.api.db.Person;

public interface BeaconPersonJpaRepository
        extends JpaRepository<Person, UUID> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM person WHERE beacon_id = ?1"
    )
    List<Person> findAllByBeaconId(UUID beaconId);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM person WHERE beacon_id = ?1 AND person_type = 'OWNER'"
    )
    Person findOwnerByBeaconId(UUID beaconId);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM person WHERE beacon_id = ?1 AND person_type = 'EMERGENCY_CONTACT'"
    )
    List<Person> findEmergencyContactsByBeaconId(UUID beaconId);
}
