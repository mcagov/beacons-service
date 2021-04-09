package uk.gov.mca.beacons.service.registrations;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconStatus;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.PersonType;
import uk.gov.mca.beacons.service.model.Registration;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Service
@Transactional
public class RegistrationsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    RegistrationsService.class
  );

  private final BeaconRepository beaconRepository;
  private final BeaconUseRepository beaconUseRepository;
  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public RegistrationsService(
    BeaconRepository beaconRepository,
    BeaconUseRepository beaconUseRepository,
    BeaconPersonRepository beaconPersonRepository
  ) {
    this.beaconRepository = beaconRepository;
    this.beaconPersonRepository = beaconPersonRepository;
    this.beaconUseRepository = beaconUseRepository;
  }

  public Registration register(Registration registration) {
    LOGGER.info("Attempting to persist registration {}", registration);

    final List<Beacon> beacons = registration.getBeacons();
    beacons.forEach(this::registerBeacon);

    return registration;
  }

  private void registerBeacon(Beacon beacon) {
    beacon.setBeaconStatus(BeaconStatus.NEW);
    final Beacon persistedBeacon = beaconRepository.save(beacon);

    registerBeaconUses(persistedBeacon);
    registerOwner(persistedBeacon);
    registerEmergencyContacts(persistedBeacon);
  }

  private void registerBeaconUses(Beacon beacon) {
    final List<BeaconUse> beaconUses = beacon.getUses();
    final UUID beaconId = beacon.getId();

    beaconUses.forEach(
      use -> {
        use.setBeaconId(beaconId);
        beaconUseRepository.save(use);
      }
    );
  }

  private void registerOwner(Beacon beacon) {
    final BeaconPerson owner = beacon.getOwner();
    final UUID beaconId = beacon.getId();

    registerPerson(owner, beaconId, PersonType.OWNER);
  }

  private void registerEmergencyContacts(Beacon beacon) {
    final List<BeaconPerson> emergencyContacts = beacon.getEmergencyContacts();
    final UUID beaconId = beacon.getId();

    emergencyContacts.forEach(
      person -> registerPerson(person, beaconId, PersonType.EMERGENCY_CONTACT)
    );
  }

  private void registerPerson(
    BeaconPerson person,
    UUID beaconId,
    PersonType personType
  ) {
    person.setBeaconId(beaconId);
    person.setPersonType(personType);
    beaconPersonRepository.save(person);
  }
}
