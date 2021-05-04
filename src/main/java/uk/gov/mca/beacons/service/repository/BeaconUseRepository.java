package uk.gov.mca.beacons.service.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.BeaconUse;

@Repository
public interface BeaconUseRepository extends JpaRepository<BeaconUse, UUID> {
  @Query(
    nativeQuery = true,
    value = "SELECT * FROM beacon_use WHERE beacon_id = ?1"
  )
  List<BeaconUse> findAllByBeaconId(UUID beaconId);
}
