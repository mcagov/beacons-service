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
    "b.hexId LIKE %:term% OR " +
    "b.beaconStatus LIKE %:term% OR " +
    "b.ownerName LIKE %:term% OR " +
    "b.useActivities LIKE %:term%" +
    ")"
  )
  Page<BeaconSearchEntity> findALl(@Param("term") String term, Pageable page);
}
