package uk.gov.mca.beacons.api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;
import uk.gov.mca.beacons.api.gateways.EmergencyContactGateway;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.CreateEmergencyContactRequestMapper;
import uk.gov.mca.beacons.api.mappers.CreateOwnerRequestMapper;

@Service
@Transactional
@Slf4j
public class CreateRegistrationService {

  private final BeaconJpaRepository beaconJpaRepository;
  private final BeaconUseJpaRepository beaconUseJpaRepository;
  private final OwnerGateway ownerGateway;
  private final EmergencyContactGateway emergencyContactGateway;
  private final LegacyBeaconService legacyBeaconService;

  @Autowired
  public CreateRegistrationService(
    BeaconJpaRepository beaconJpaRepository,
    BeaconUseJpaRepository beaconUseJpaRepository,
    OwnerGateway ownerGateway,
    EmergencyContactGateway emergencyContactGateway,
    LegacyBeaconService legacyBeaconService
  ) {
    this.beaconJpaRepository = beaconJpaRepository;
    this.beaconUseJpaRepository = beaconUseJpaRepository;
    this.ownerGateway = ownerGateway;
    this.emergencyContactGateway = emergencyContactGateway;
    this.legacyBeaconService = legacyBeaconService;
  }

  public Beacon register(Beacon beacon) {
    log.info("Attempting to persist registration {}", beacon);

    Optional<List<LegacyBeacon>> matchingLegacyBeacons = legacyBeaconService.findMatchingLegacyBeacons(
      beacon
    );

    matchingLegacyBeacons.ifPresent(
      matches -> matches.forEach(legacyBeaconService::claim)
    );

    registerBeacon(beacon);

    return beacon;
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
