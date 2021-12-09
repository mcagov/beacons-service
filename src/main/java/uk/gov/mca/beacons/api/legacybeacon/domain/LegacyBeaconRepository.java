package uk.gov.mca.beacons.api.legacybeacon.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LegacyBeaconRepository
  extends JpaRepository<LegacyBeacon, LegacyBeaconId> {}
