package uk.gov.mca.beacons.api.gateways;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.jpa.LegacyBeaconJpaRepository;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;

@Repository
@Transactional
public class LegacyBeaconGatewayImpl implements LegacyBeaconGateway {

  private final LegacyBeaconJpaRepository legacyBeaconJpaRepository;
  private final LegacyBeaconMapper legacyBeaconMapper;

  @Autowired
  public LegacyBeaconGatewayImpl(
    LegacyBeaconJpaRepository legacyBeaconJpaRepository,
    LegacyBeaconMapper legacyBeaconMapper
  ) {
    this.legacyBeaconJpaRepository = legacyBeaconJpaRepository;
    this.legacyBeaconMapper = legacyBeaconMapper;
  }

  @Override
  public LegacyBeacon save(LegacyBeacon beacon) {
    final var legacyBeaconEntity = legacyBeaconMapper.toJpaEntity(beacon);
    legacyBeaconEntity.setBeaconStatus(BeaconStatus.MIGRATED);

    return legacyBeaconMapper.fromJpaEntity(
      legacyBeaconJpaRepository.save(legacyBeaconEntity)
    );
  }

  @Override
  public void deleteAll() {
    legacyBeaconJpaRepository.deleteAll();
    legacyBeaconJpaRepository.flush();
  }
}
