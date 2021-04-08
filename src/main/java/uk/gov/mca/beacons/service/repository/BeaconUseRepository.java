package uk.gov.mca.beacons.service.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.BeaconUse;

@Repository
public interface BeaconUseRepository extends CrudRepository<BeaconUse, UUID> {}
