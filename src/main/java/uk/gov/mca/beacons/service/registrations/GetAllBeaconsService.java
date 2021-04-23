package uk.gov.mca.beacons.service.registrations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.PersonType;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Service
@Transactional
public class GetAllBeaconsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(GetAllBeaconsService.class);

  private final BeaconRepository beaconRepository;
  private final BeaconUseRepository beaconUseRepository;
  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public GetAllBeaconsService(BeaconRepository beaconRepository, BeaconUseRepository beaconUseRepository,
      BeaconPersonRepository beaconPersonRepository) {
    this.beaconRepository = beaconRepository;
    this.beaconPersonRepository = beaconPersonRepository;
    this.beaconUseRepository = beaconUseRepository;
  }

  public List<Beacon> findAll() {
    final var allBeacons = new ArrayList<Beacon>();
    final var result = beaconRepository.findAll();
    result.forEach(allBeacons::add);

    final var usesStream = StreamSupport.stream(beaconUseRepository.findAll().spliterator(), false);
    final var groupedUsesByBeacon = usesStream.collect(Collectors.groupingBy(BeaconUse::getBeaconId));

    final var personStream = StreamSupport.stream(beaconPersonRepository.findAll().spliterator(), false);
    final var groupedPersonsByType = personStream.collect(Collectors.groupingBy(BeaconPerson::getPersonType));
    final var allOwners = groupedPersonsByType.get(PersonType.OWNER);
    final var emergencyContactSteam = groupedPersonsByType.get(PersonType.EMERGENCY_CONTACT).stream();
    final var emergencyContactsByBeacon = emergencyContactSteam
        .collect(Collectors.groupingBy(BeaconPerson::getBeaconId));

    allBeacons.forEach(beacon -> {
      var beaconUses = groupedUsesByBeacon.get(beacon.getId());
      beacon.setUses(beaconUses);

      var beaconOwnerOption = allOwners.stream().filter(o -> o.getBeaconId() == beacon.getId()).findFirst();
      if (!beaconOwnerOption.isEmpty()) {
        beacon.setOwner(beaconOwnerOption.get());
      }

      beacon.setEmergencyContacts(emergencyContactsByBeacon.get(beacon.getId()));

    });

    return allBeacons;
  }
}
