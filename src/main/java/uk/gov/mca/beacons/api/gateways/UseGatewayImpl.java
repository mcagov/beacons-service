package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;

@Repository
@Transactional
public class UseGatewayImpl implements UseGateway {

  private final BeaconUseJpaRepository beaconUseJpaRepository;

  @Autowired
  public UseGatewayImpl(BeaconUseJpaRepository beaconUseJpaRepository) {
    this.beaconUseJpaRepository = beaconUseJpaRepository;
  }

  @Override
  public List<BeaconUse> findAllByBeaconId(UUID beaconId) {
    return beaconUseJpaRepository.findAllByBeaconId(beaconId);
  }

  @Override
  public void deleteAllByBeaconId(UUID beaconId) {
    beaconUseJpaRepository.deleteAllByBeaconId(beaconId);
  }

  @Override
  public BeaconUse save(BeaconUse use) {
    return beaconUseJpaRepository.save(use);
  }
}
