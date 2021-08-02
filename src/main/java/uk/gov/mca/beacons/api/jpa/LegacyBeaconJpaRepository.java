package uk.gov.mca.beacons.api.jpa;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;

public interface LegacyBeaconJpaRepository
  extends JpaRepository<LegacyBeaconEntity, UUID> {
  @Override
  void deleteAll();
}
