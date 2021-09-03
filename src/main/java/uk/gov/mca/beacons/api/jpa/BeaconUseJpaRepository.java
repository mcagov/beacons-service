package uk.gov.mca.beacons.api.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;

public interface BeaconUseJpaRepository extends JpaRepository<BeaconUse, UUID> {
  @Query(
    nativeQuery = true,
    value = "SELECT * FROM beacon_use WHERE beacon_id = ?1"
  )
  List<BeaconUse> findAllByBeaconId(UUID beaconId);

  void deleteAllByBeaconId(UUID beaconId);
}
