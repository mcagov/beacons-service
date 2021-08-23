package uk.gov.mca.beacons.api.controllers;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.gov.mca.beacons.api.jpa.entities.BeaconSearchEntity;

/**
 * This {@link RepositoryRestResource} exposes controller endpoints to enable searching across beacon records using JPA's built in pagination/sorting capability.
 * <p>
 * See the docs: https://docs.spring.io/spring-data/rest/docs/current/reference/html/#reference for more info
 */
@RepositoryRestResource(
  path = "beacon-search",
  collectionResourceRel = "beaconSearch"
)
interface BeaconSearchRestRepository
  extends JpaRepository<BeaconSearchEntity, UUID> {
  @RestResource(path = "find-all", rel = "findAllBeacons")
  @Query(
    "SELECT b FROM BeaconSearchEntity b WHERE " +
    "(" +
    "COALESCE(LOWER(b.hexId), '') LIKE LOWER(CONCAT('%', :term, '%')) OR " +
    "COALESCE(LOWER(b.beaconStatus), '') LIKE LOWER(CONCAT('%', :term, '%')) OR " +
    "COALESCE(LOWER(b.ownerName), '') LIKE LOWER(CONCAT('%', :term, '%')) OR " +
    "COALESCE(LOWER(b.useActivities), '') LIKE LOWER(CONCAT('%', :term, '%')) " +
    ") " +
    "AND (COALESCE(LOWER(b.beaconStatus), '') LIKE LOWER(CONCAT('%', :status, '%'))) " +
    "AND (COALESCE(LOWER(b.useActivities), '') LIKE LOWER(CONCAT('%', :uses, '%')))"
  )
  Page<BeaconSearchEntity> findALl(
    @Param("term") String term,
    @Param("status") String status,
    @Param("uses") String uses,
    Pageable page
  );
}
