package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@Repository
@Transactional
public class BeaconGatewayImpl implements BeaconGateway {

  private final BeaconJpaRepository beaconJpaRepository;
  private final AuditingHandler auditingHandler;

  @Autowired
  public BeaconGatewayImpl(
    BeaconJpaRepository beaconJpaRepository,
    AuditingHandler auditingHandler
  ) {
    this.beaconJpaRepository = beaconJpaRepository;
    this.auditingHandler = auditingHandler;
  }

  @Override
  public Optional<Beacon> findById(UUID id) {
    return beaconJpaRepository.findById(id);
  }

  @Override
  public Beacon update(Beacon beacon) {
    auditingHandler.markModified(beacon);
    return beaconJpaRepository.save(beacon);
  }

  @Override
  public List<Beacon> findAllActiveBeaconsByAccountHolderId(UUID accountId) {
    return beaconJpaRepository.findAllActiveBeaconsByAccountHolderId(accountId);
  }

  @Override
  public void delete(UUID id) {
    final Beacon beacon = beaconJpaRepository
      .findById(id)
      .orElseThrow(ResourceNotFoundException::new);

    beacon.setBeaconStatus(BeaconStatus.DELETED);
    beaconJpaRepository.save(beacon);
  }
}
