package uk.gov.mca.beacons.api.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;

@Service
@Transactional
public class DeleteBeaconService {

  private final BeaconGateway beaconGateway;

  @Autowired
  public DeleteBeaconService(BeaconGateway beaconGateway) {
    this.beaconGateway = beaconGateway;
  }

  public void delete(UUID beaconId) {
    beaconGateway.delete(beaconId);
  }
}
