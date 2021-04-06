package uk.gov.mca.beacons.service.registrations;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@RepositoryRestResource(exported = false)
public interface BeaconPersonRepository
  extends CrudRepository<BeaconPerson, UUID> {}
