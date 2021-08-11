package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.gov.mca.beacons.api.jpa.entities.BeaconSearchEntity;

@RepositoryRestResource(
  path = "beacon-search",
  collectionResourceRel = "beaconSearch"
)
public interface BeaconSearchRestRepository
  extends JpaRepository<BeaconSearchEntity, UUID> {
  @RestResource(path = "find-all", rel = "findAllBeacons")
  Page<BeaconSearchEntity> findAllBy(Pageable page);
}
