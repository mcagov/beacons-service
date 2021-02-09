package uk.gov.mca.beacons.service.model;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "beacon-uses")
public interface BeaconUseRepository extends CrudRepository<BeaconUse, UUID> {}
