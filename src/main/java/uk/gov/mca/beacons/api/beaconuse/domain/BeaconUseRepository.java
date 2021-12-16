package uk.gov.mca.beacons.api.beaconuse.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.mca.beacons.api.beacon.domain.BeaconId;

public interface BeaconUseRepository
  extends JpaRepository<BeaconUse, BeaconUseId> {
  void deleteAllByBeaconId(BeaconId beaconId);
}
