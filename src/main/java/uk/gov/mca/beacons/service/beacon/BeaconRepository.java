package uk.gov.mca.beacons.service.beacon;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconRepository extends CrudRepository<Beacon, UUID> {}
