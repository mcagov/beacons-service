package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.gov.mca.beacons.api.jpa.entities.BeaconSearchEntity;

@RepositoryRestResource(
  path = "beacon-search",
  collectionResourceRel = "beacon-search"
)
interface BeaconSearchRestRepository
  extends JpaRepository<BeaconSearchEntity, UUID> {
  @RestResource(path = "find-all", rel = "findAllBeacons")
  @Query(
    "SELECT b FROM BeaconSearchEntity b WHERE " +
    "(" +
    "LOWER(b.hexId) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
    "LOWER(b.beaconStatus) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
    "LOWER(b.ownerName) LIKE LOWER(CONCAT('%', :term, '%')) OR " +
    "LOWER(b.useActivities) LIKE LOWER(CONCAT('%', :term, '%')) " +
    ") AND (LOWER(b.beaconStatus) LIKE LOWER(CONCAT('%', :status, '%'))) " +
    "AND (LOWER(b.useActivities) LIKE LOWER(CONCAT('%', :uses, '%')))"
  )
  Page<BeaconSearchEntity> findALl(
    @Param("term") String term,
    @Param("status") String status,
    @Param("uses") String uses,
    Pageable page
  );
}
