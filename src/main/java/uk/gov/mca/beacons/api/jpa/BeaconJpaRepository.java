package uk.gov.mca.beacons.api.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.gov.mca.beacons.api.db.Beacon;

public interface BeaconJpaRepository extends JpaRepository<Beacon, UUID> {
  @Query(
    nativeQuery = true,
    value = "SELECT * FROM beacon WHERE account_holder_id = ?1"
  )
  List<Beacon> findAllByAccountHolderId(UUID accountId);
}
