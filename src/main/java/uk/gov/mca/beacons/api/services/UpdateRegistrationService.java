package uk.gov.mca.beacons.api.services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.dto.UpdateRegistrationRequest;
import uk.gov.mca.beacons.api.gateways.UseGateway;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;

@Service
@Transactional
public class UpdateRegistrationService {

  private final BeaconsService beaconsService;
  private final UseGateway useGateway;

  @Autowired
  public UpdateRegistrationService(
    BeaconsService beaconsService,
    UseGateway useGateway
  ) {
    this.beaconsService = beaconsService;
    this.useGateway = useGateway;
  }

  public Beacon update(UpdateRegistrationRequest request) {
    final UUID beaconId = request.getBeaconId();
    final Beacon beacon = request.getBeacon();

    beaconsService.update(beaconId, beacon);
    replaceUses(beaconId, beacon.getUses());

    return beaconsService.find(beaconId);
  }

  private void replaceUses(UUID beaconId, List<BeaconUse> uses) {
    useGateway.deleteAllByBeaconId(beaconId);
    uses.forEach(
      use -> {
        use.setBeaconId(beaconId);
        useGateway.save(use);
      }
    );
  }
}
