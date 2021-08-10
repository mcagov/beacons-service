package uk.gov.mca.beacons.api.jpa;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;

public interface LegacyBeaconJpaRepository
  extends CrudRepository<LegacyBeaconEntity, UUID> {}
