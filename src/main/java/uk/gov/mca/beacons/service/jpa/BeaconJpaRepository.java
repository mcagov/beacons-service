package uk.gov.mca.beacons.service.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.db.Beacon;

@Repository
public interface BeaconJpaRepository extends JpaRepository<Beacon, UUID> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM beacon WHERE account_holder_id = ?1"
    )
    List<Beacon> findAllByAccountHolderId(UUID accountId);
}
