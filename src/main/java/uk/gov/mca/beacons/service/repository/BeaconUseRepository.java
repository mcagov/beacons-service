package uk.gov.mca.beacons.service.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.mca.beacons.service.model.BeaconUse;

@RepositoryRestResource(path = "beacon-uses")
public interface BeaconUseRepository extends CrudRepository<BeaconUse, UUID> {}
