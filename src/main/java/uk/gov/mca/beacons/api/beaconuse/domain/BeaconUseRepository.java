package uk.gov.mca.beacons.api.beaconuse.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeaconUseRepository
  extends JpaRepository<BeaconUse, BeaconUseId> {}
