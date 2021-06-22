package uk.gov.mca.beacons.api.services;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.db.Beacon;
import uk.gov.mca.beacons.api.db.BeaconUse;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;
import uk.gov.mca.beacons.api.gateways.EmergencyContactGateway;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.gateways.UseGateway;

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

                    final Person owner = ownerGateway.findByBeaconId(beaconId);
                    beacon.setOwner(owner);

                    final List<Person> emergencyContacts = emergencyContactGateway.findAllByBeaconId(
                            beaconId
                    );
                    beacon.setEmergencyContacts(emergencyContacts);
                }
        );

        return beacons;
    }
}
