package uk.gov.mca.beacons.service.accounts;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.gateway.BeaconGateway;
import uk.gov.mca.beacons.service.gateway.EmergencyContactGateway;
import uk.gov.mca.beacons.service.gateway.OwnerGateway;
import uk.gov.mca.beacons.service.gateway.UseGateway;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;

@Service
public class GetBeaconsByAccountHolderIdService {

  private final BeaconGateway beaconGateway;
  private final UseGateway useGateway;
  private final OwnerGateway ownerGateway;
  private final EmergencyContactGateway emergencyContactGateway;

  @Autowired
  public GetBeaconsByAccountHolderIdService(
    BeaconGateway beaconGateway,
    UseGateway useGateway,
    OwnerGateway ownerGateway,
    EmergencyContactGateway emergencyContactGateway
  ) {
    this.beaconGateway = beaconGateway;
    this.useGateway = useGateway;
    this.ownerGateway = ownerGateway;
    this.emergencyContactGateway = emergencyContactGateway;
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

        final BeaconPerson owner = ownerGateway.findByBeaconId(beaconId);
        beacon.setOwner(owner);

        final List<BeaconPerson> emergencyContacts = emergencyContactGateway.findAllByBeaconId(
          beaconId
        );
        beacon.setEmergencyContacts(emergencyContacts);
      }
    );

    return beacons;
  }
}
