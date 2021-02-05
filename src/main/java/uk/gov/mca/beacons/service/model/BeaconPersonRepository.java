package uk.gov.mca.beacons.service.model;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconPersonRepository
  extends CrudRepository<BeaconPerson, UUID> {}
