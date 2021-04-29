package uk.gov.mca.beacons.service.registrations;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
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
    final List<Beacon> allBeacons = beaconRepository.findAll();
    if (allBeacons.size() == 0) return emptyList();

    final Map<UUID, List<BeaconUse>> usesGroupedByBeaconId = getAllUsesGroupedByBeaconId();
    final Map<PersonType, List<BeaconPerson>> personsGroupedByType = getAllPersonsGroupedByType();
    final Map<UUID, BeaconPerson> ownersGroupedByBeaconId = getAllOwnersGroupedByBeaconId(
      personsGroupedByType
    );
    final Map<UUID, List<BeaconPerson>> emergencyContactsGroupedByBeaconId = getAllContactsGroupedByBeaconId(
      personsGroupedByType
    );

    return mapBeaconRelationships(
      allBeacons,
      usesGroupedByBeaconId,
      ownersGroupedByBeaconId,
      emergencyContactsGroupedByBeaconId
    );
  }

  public Beacon find(UUID id) {
    final Optional<Beacon> beacon = beaconRepository.findById(id);

    return beacon
      .map(
        foundBeacon -> {
          return getMappedBeacon(foundBeacon);
        }
      )
      .orElse(new Beacon());
  }

  private Beacon getMappedBeacon(Beacon beacon) {
    List<Beacon> beaconInList = new ArrayList<Beacon>();
    beaconInList.add(beacon);

    final Map<UUID, List<BeaconUse>> usesGroupedByBeaconId = getAllUsesGroupedByBeaconId();
    final Map<PersonType, List<BeaconPerson>> personsGroupedByType = getAllPersonsGroupedByType();
    final Map<UUID, BeaconPerson> ownersGroupedByBeaconId = getAllOwnersGroupedByBeaconId(
      personsGroupedByType
    );
    final Map<UUID, List<BeaconPerson>> emergencyContactsGroupedByBeaconId = getAllContactsGroupedByBeaconId(
      personsGroupedByType
    );

    return mapBeaconRelationships(
      beaconInList,
      usesGroupedByBeaconId,
      ownersGroupedByBeaconId,
      emergencyContactsGroupedByBeaconId
    )
      .get(0);
  }

  private List<Beacon> mapBeaconRelationships(
    final List<Beacon> allBeacons,
    final Map<UUID, List<BeaconUse>> usesGroupedByBeaconId,
    final Map<UUID, BeaconPerson> ownersGroupedByBeaconId,
    final Map<UUID, List<BeaconPerson>> emergencyContactsGroupedByBeaconId
  ) {
    List<Beacon> mappedBeacons = new ArrayList<Beacon>();

    allBeacons.forEach(
      beacon -> {
        var beaconUses = usesGroupedByBeaconId.get(beacon.getId());
        beacon.setUses(beaconUses);
        var beaconOwner = ownersGroupedByBeaconId.get(beacon.getId());
        beacon.setOwner(beaconOwner);
        beacon.setEmergencyContacts(
          emergencyContactsGroupedByBeaconId.get(beacon.getId())
        );
        mappedBeacons.add(beacon);
      }
    );

    return mappedBeacons;
  }

  private Map<UUID, List<BeaconUse>> getAllUsesGroupedByBeaconId() {
    final var usesStream = beaconUseRepository.findAll().stream();
    final Map<UUID, List<BeaconUse>> usesGroupedByBeaconId = usesStream.collect(
      Collectors.groupingBy(BeaconUse::getBeaconId)
    );
    return usesGroupedByBeaconId;
  }

  private Map<UUID, List<BeaconPerson>> getAllContactsGroupedByBeaconId(
    final Map<PersonType, List<BeaconPerson>> personsGroupedByType
  ) {
    final var emergencyContacts = personsGroupedByType.get(
      PersonType.EMERGENCY_CONTACT
    );
    if (emergencyContacts == null) return emptyMap();

    final var emergencyContactStream = emergencyContacts.stream();
    final Map<UUID, List<BeaconPerson>> emergencyContactsGroupedByBeaconId = emergencyContactStream.collect(
      Collectors.groupingBy(BeaconPerson::getBeaconId)
    );
    return emergencyContactsGroupedByBeaconId;
  }

  private Map<UUID, BeaconPerson> getAllOwnersGroupedByBeaconId(
    final Map<PersonType, List<BeaconPerson>> personsGroupedByType
  ) {
    final var owners = personsGroupedByType.get(PersonType.OWNER);
    if (owners == null) return emptyMap();

    var ownersGroupedByBeaconId = new HashMap<UUID, BeaconPerson>();
    owners.forEach(
      owner -> ownersGroupedByBeaconId.putIfAbsent(owner.getBeaconId(), owner)
    );

    return ownersGroupedByBeaconId;
  }

  private Map<PersonType, List<BeaconPerson>> getAllPersonsGroupedByType() {
    final var personStream = beaconPersonRepository.findAll().stream();
    final var personsGroupedByType = personStream.collect(
      Collectors.groupingBy(BeaconPerson::getPersonType)
    );
    return personsGroupedByType;
  }
}
