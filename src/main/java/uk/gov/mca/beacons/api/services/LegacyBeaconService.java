package uk.gov.mca.beacons.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;

@Service
@Transactional
public class LegacyBeaconService {

  private final LegacyBeaconGateway legacyBeaconGateway;

  @Autowired
  public LegacyBeaconService(LegacyBeaconGateway legacyBeaconGateway) {
    this.legacyBeaconGateway = legacyBeaconGateway;
  }

  public LegacyBeacon create(LegacyBeacon beacon) {
    return legacyBeaconGateway.save(beacon);
  }

  public void deleteAll() {
    legacyBeaconGateway.deleteAll();
  }
}
