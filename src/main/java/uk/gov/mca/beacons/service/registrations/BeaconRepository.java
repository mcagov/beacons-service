package uk.gov.mca.beacons.service.registrations;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.Beacon;

@Repository
public interface BeaconRepository extends CrudRepository<Beacon, UUID> {}
