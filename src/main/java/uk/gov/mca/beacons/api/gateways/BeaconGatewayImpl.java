package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@Repository
@Transactional
public class BeaconGatewayImpl implements BeaconGateway {

  private final BeaconJpaRepository beaconJpaRepository;

  @Autowired
  public BeaconGatewayImpl(BeaconJpaRepository beaconJpaRepository) {
    this.beaconJpaRepository = beaconJpaRepository;
  }

  @Override
  public List<Beacon> findAllByAccountHolderId(UUID accountId) {
    return beaconJpaRepository.findAllByAccountHolderId(accountId);
  }

  @Override
  public List<Beacon> findAllActiveBeaconsByAccountHolderId(UUID accountId) {
    return beaconJpaRepository.findAllActiveBeaconsByAccountHolderId(accountId);
  }

  @Override
  public void delete(UUID beaconId) {
    final Beacon beacon = beaconJpaRepository
      .findById(beaconId)
      .orElseThrow(ResourceNotFoundException::new);

    beacon.setBeaconStatus(BeaconStatus.DELETED);
    beaconJpaRepository.save(beacon);
  }
}
