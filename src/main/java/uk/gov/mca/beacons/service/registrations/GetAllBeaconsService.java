package uk.gov.mca.beacons.service.registrations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(
    GetAllBeaconsService.class
  );

  private final BeaconRepository beaconRepository;
  private final BeaconUseRepository beaconUseRepository;
  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public GetAllBeaconsService(
    BeaconRepository beaconRepository,
    BeaconUseRepository beaconUseRepository,
    BeaconPersonRepository beaconPersonRepository
  ) {
    this.beaconRepository = beaconRepository;
    this.beaconPersonRepository = beaconPersonRepository;
    this.beaconUseRepository = beaconUseRepository;
  }

  public List<Beacon> findAll() {
    final ArrayList<Beacon> allBeacons = getAllBeacons();
    if (allBeacons.size() == 0) return List.of();

    final Map<UUID, List<BeaconUse>> groupedUsesByBeacon = getAllUsesGroupedByBeaconId();

    final Map<PersonType, List<BeaconPerson>> groupedPersonsByType = getAllPersonsGroupedByType();
    final Map<UUID, BeaconPerson> allOwners = getAllOwnersMappedByBeaconId(
      groupedPersonsByType
    );
    final Map<UUID, List<BeaconPerson>> emergencyContactsByBeacon = getAllContactsGroupedByBeaconId(
      groupedPersonsByType
    );

    return mapBeaconRelationships(
      allBeacons,
      groupedUsesByBeacon,
      allOwners,
      emergencyContactsByBeacon
    );
  }

  private List<Beacon> mapBeaconRelationships(
    final ArrayList<Beacon> allBeacons,
    final Map<UUID, List<BeaconUse>> usesGroupedByBeacon,
    final Map<UUID, BeaconPerson> allOwners,
    final Map<UUID, List<BeaconPerson>> emergencyContactsByBeacon
  ) {
    var mappedBeacons = new ArrayList<Beacon>();

    allBeacons.forEach(
      beacon -> {
        var beaconUses = usesGroupedByBeacon.get(beacon.getId());
        beacon.setUses(beaconUses);
        var beaconOwner = allOwners.get(beacon.getId());
        beacon.setOwner(beaconOwner);
        beacon.setEmergencyContacts(
          emergencyContactsByBeacon.get(beacon.getId())
        );
        mappedBeacons.add(beacon);
      }
    );

    return mappedBeacons;
  }

  private Map<UUID, List<BeaconUse>> getAllUsesGroupedByBeaconId() {
    final var usesStream = StreamSupport.stream(
      beaconUseRepository.findAll().spliterator(),
      false
    );
    final Map<UUID, List<BeaconUse>> groupedUsesByBeacon = usesStream.collect(
      Collectors.groupingBy(BeaconUse::getBeaconId)
    );
    return groupedUsesByBeacon;
  }

  private Map<UUID, List<BeaconPerson>> getAllContactsGroupedByBeaconId(
    final Map<PersonType, List<BeaconPerson>> groupedPersonsByType
  ) {
    final var emergencyContacts = groupedPersonsByType.get(
      PersonType.EMERGENCY_CONTACT
    );
    if (
      emergencyContacts == null
    ) return new HashMap<UUID, List<BeaconPerson>>();

    final var emergencyContactSteam = emergencyContacts.stream();
    final Map<UUID, List<BeaconPerson>> emergencyContactsByBeacon = emergencyContactSteam.collect(
      Collectors.groupingBy(BeaconPerson::getBeaconId)
    );
    return emergencyContactsByBeacon;
  }

  private Map<UUID, BeaconPerson> getAllOwnersMappedByBeaconId(
    final Map<PersonType, List<BeaconPerson>> groupedPersonsByType
  ) {
    final var owners = groupedPersonsByType.get(PersonType.OWNER);
    if (owners == null) return new HashMap<UUID, BeaconPerson>();

    var mappedOwners = new HashMap<UUID, BeaconPerson>();
    owners.forEach(
      owner -> mappedOwners.putIfAbsent(owner.getBeaconId(), owner)
    );

    return mappedOwners;
  }

  private Map<PersonType, List<BeaconPerson>> getAllPersonsGroupedByType() {
    final var personStream = StreamSupport.stream(
      beaconPersonRepository.findAll().spliterator(),
      false
    );
    final var groupedPersonsByType = personStream.collect(
      Collectors.groupingBy(BeaconPerson::getPersonType)
    );
    return groupedPersonsByType;
  }

  private ArrayList<Beacon> getAllBeacons() {
    final var allBeacons = new ArrayList<Beacon>();
    final var result = beaconRepository.findAll();
    result.forEach(allBeacons::add);
    return allBeacons;
  }
}
