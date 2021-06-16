package uk.gov.mca.beacons.service.accounts;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.gateway.BeaconGateway;
import uk.gov.mca.beacons.service.gateway.UseGateway;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconUse;

@Service
public class GetBeaconsByAccountHolderIdService {

  private final BeaconGateway beaconGateway;
  private final UseGateway useGateway;

  @Autowired
  public GetBeaconsByAccountHolderIdService(
    BeaconGateway beaconGateway,
    UseGateway useGateway
  ) {
    this.beaconGateway = beaconGateway;
    this.useGateway = useGateway;
  }

  public List<Beacon> execute(UUID accountId) {
    final List<Beacon> beacons = beaconGateway.findAllByAccountHolderId(
      accountId
    );
    if (beacons.isEmpty()) return emptyList();

    beacons.forEach(
      beacon -> {
        final UUID beaconId = beacon.getId();
        final List<BeaconUse> uses = useGateway.findAllByBeaconId(beaconId);
        beacon.setUses(uses);
      }
    );

    return beacons;
  }
}
