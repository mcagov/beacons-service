package uk.gov.mca.beacons.service.registrations;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.db.Beacon;
import uk.gov.mca.beacons.service.db.BeaconUse;
import uk.gov.mca.beacons.service.db.Person;
import uk.gov.mca.beacons.service.db.Registration;
import uk.gov.mca.beacons.service.entities.BeaconStatus;
import uk.gov.mca.beacons.service.gateway.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.service.gateway.CreateOwnerRequest;
import uk.gov.mca.beacons.service.gateway.EmergencyContactGateway;
import uk.gov.mca.beacons.service.gateway.OwnerGateway;
import uk.gov.mca.beacons.service.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.service.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.service.mappers.CreateEmergencyContactRequestMapper;
import uk.gov.mca.beacons.service.mappers.CreateOwnerRequestMapper;

@Service
@Transactional
@Slf4j
public class RegistrationsService {

    private final BeaconJpaRepository beaconJpaRepository;
    private final BeaconUseJpaRepository beaconUseJpaRepository;
    private final OwnerGateway ownerGateway;
    private final EmergencyContactGateway emergencyContactGateway;

    @Autowired
    public RegistrationsService(
            BeaconJpaRepository beaconJpaRepository,
            BeaconUseJpaRepository beaconUseJpaRepository,
            OwnerGateway ownerGateway,
            EmergencyContactGateway emergencyContactGateway
    ) {
        this.beaconJpaRepository = beaconJpaRepository;
        this.beaconUseJpaRepository = beaconUseJpaRepository;
        this.ownerGateway = ownerGateway;
        this.emergencyContactGateway = emergencyContactGateway;
    }

    public Registration register(Registration registration) {
        log.info("Attempting to persist registration {}", registration);

        final List<Beacon> beacons = registration.getBeacons();
        beacons.forEach(this::registerBeacon);

        return registration;
    }

    private void registerBeacon(Beacon beacon) {
        beacon.setBeaconStatus(BeaconStatus.NEW);
        final Beacon persistedBeacon = beaconJpaRepository.save(beacon);
        final UUID beaconId = persistedBeacon.getId();

        registerBeaconUses(persistedBeacon.getUses(), beaconId);
        registerOwner(persistedBeacon.getOwner(), beaconId);
        registerEmergencyContacts(persistedBeacon.getEmergencyContacts(), beaconId);
    }

    private void registerBeaconUses(List<BeaconUse> uses, UUID beaconId) {
        uses.forEach(
                use -> {
                    use.setBeaconId(beaconId);
                    beaconUseJpaRepository.save(use);
                }
        );
    }

    private void registerOwner(Person owner, UUID beaconId) {
        final CreateOwnerRequest request = CreateOwnerRequestMapper.fromBeaconPerson(
                owner,
                beaconId
        );
        ownerGateway.save(request);
    }

    private void registerEmergencyContacts(
            List<Person> emergencyContacts,
            UUID beaconId
    ) {
        emergencyContacts.forEach(
                emergencyContact -> registerEmergencyContact(emergencyContact, beaconId)
        );
    }

    private void registerEmergencyContact(
            Person emergencyContact,
            UUID beaconId
    ) {
        final CreateEmergencyContactRequest request = CreateEmergencyContactRequestMapper.fromBeaconPerson(
                emergencyContact,
                beaconId
        );
        emergencyContactGateway.save(request);
    }
}
