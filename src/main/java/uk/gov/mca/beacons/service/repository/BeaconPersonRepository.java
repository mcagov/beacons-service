package uk.gov.mca.beacons.service.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@Repository
public interface BeaconPersonRepository
  extends JpaRepository<BeaconPerson, UUID> {
  @Query(
    nativeQuery = true,
    value = "SELECT * FROM person WHERE beacon_id = ?1"
  )
  List<BeaconPerson> findAllByBeaconId(UUID beaconId);
}
