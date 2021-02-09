package uk.gov.mca.beacons.service.model;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface BeaconPersonRepository
  extends CrudRepository<BeaconPerson, UUID> {}
