package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

public interface LegacyBeaconGateway {
  LegacyBeacon save(LegacyBeacon beacon);

  void deleteAll();

  List<LegacyBeacon> findAll();
}
