package uk.gov.mca.beacons.service.beacon_person;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconPersonRepository
  extends CrudRepository<BeaconPerson, UUID> {}
