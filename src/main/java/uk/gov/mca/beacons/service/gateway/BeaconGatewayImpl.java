package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.repository.BeaconRepository;

@Repository
@Transactional
public class BeaconGatewayImpl implements BeaconGateway {

  private final BeaconRepository beaconRepository;

  @Autowired
  public BeaconGatewayImpl(BeaconRepository beaconRepository) {
    this.beaconRepository = beaconRepository;
  }

  @Override
  public List<Beacon> findAllByAccountHolderId(UUID accountId) {
    return beaconRepository.findAllByAccountHolderId(accountId);
  }
}
