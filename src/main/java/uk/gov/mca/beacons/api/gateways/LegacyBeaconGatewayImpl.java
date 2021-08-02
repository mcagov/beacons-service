package uk.gov.mca.beacons.api.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.jpa.LegacyBeaconJpaRepository;

@Repository
@Transactional
public class LegacyBeaconGatewayImpl implements LegacyBeaconGateway {

  private final LegacyBeaconJpaRepository legacyBeaconJpaRepository;

  @Autowired
  public LegacyBeaconGatewayImpl(
    LegacyBeaconJpaRepository legacyBeaconJpaRepository
  ) {
    this.legacyBeaconJpaRepository = legacyBeaconJpaRepository;
  }

  @Override
  public LegacyBeacon save(LegacyBeacon beacon) {
    return null;
  }

  @Override
  public void deleteAll() {
    legacyBeaconJpaRepository.deleteAll();
  }
}
