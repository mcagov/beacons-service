package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Repository
@Transactional
public class UseGatewayImpl implements UseGateway {

  private final BeaconUseRepository beaconUseRepository;

  @Autowired
  public UseGatewayImpl(BeaconUseRepository beaconUseRepository) {
    this.beaconUseRepository = beaconUseRepository;
  }

  @Override
  public List<BeaconUse> findAllByBeaconId(UUID beaconId) {
    return beaconUseRepository.findAllByBeaconId(beaconId);
  }
}
