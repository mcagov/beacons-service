package uk.gov.mca.beacons.api.jpa;

import java.util.UUID;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;

public interface LegacyBeaconJpaRepository
  extends JpaRepository<LegacyBeaconEntity, UUID> {}
