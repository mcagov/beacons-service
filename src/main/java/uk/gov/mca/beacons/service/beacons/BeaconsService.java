package uk.gov.mca.beacons.service.beacons;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.mappers.ModelPatcher;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Service
@Transactional
public class BeaconsService {

  private final BeaconRepository beaconRepository;
  private final BeaconPersonRepository beaconPersonRepository;
  private final BeaconUseRepository beaconUseRepository;
  private final BeaconsRelationshipMapper beaconsRelationshipMapper;

  @Autowired
  public BeaconsService(
    BeaconRepository beaconRepository,
    BeaconPersonRepository beaconPersonRepository,
    BeaconUseRepository beaconUseRepository,
    BeaconsRelationshipMapper beaconsRelationshipMapper
  ) {
    this.beaconRepository = beaconRepository;
    this.beaconPersonRepository = beaconPersonRepository;
    this.beaconUseRepository = beaconUseRepository;
    this.beaconsRelationshipMapper = beaconsRelationshipMapper;
  }

  public List<Beacon> findAll() {
    final List<Beacon> allBeacons = beaconRepository.findAll();
    if (allBeacons.size() == 0) return emptyList();

    final List<BeaconPerson> allBeaconPersons = beaconPersonRepository.findAll();
    final List<BeaconUse> allBeaconUses = beaconUseRepository.findAll();

    return beaconsRelationshipMapper.getMappedBeacons(
      allBeacons,
      allBeaconPersons,
      allBeaconUses
    );
  }

  public Beacon find(UUID id) {
    final Optional<Beacon> beaconResult = beaconRepository.findById(id);
    if (beaconResult.isEmpty()) return null;

    final Beacon beacon = beaconResult.get();
    final List<BeaconPerson> beaconPersons = beaconPersonRepository.findAllByBeaconId(
      id
    );
    final List<BeaconUse> beaconUses = beaconUseRepository.findAllByBeaconId(
      id
    );

    return beaconsRelationshipMapper.getMappedBeacon(
      beacon,
      beaconPersons,
      beaconUses
    );
  }

  public void update(UUID id, Beacon update) {
    final Beacon beacon = this.find(id); // TODO: we HAVE TO populate the uses/persons to allow validation on save,
    // inefficient :(
    if (beacon == null) throw new RuntimeException(); // TODO: pick an exception

    final var patcher = new ModelPatcher<Beacon>()
      .addMapping(Beacon::getBatteryExpiryDate, Beacon::setBatteryExpiryDate)
      .addMapping(Beacon::getBeaconStatus, Beacon::setBeaconStatus)
      .addMapping(Beacon::getChkCode, Beacon::setChkCode)
      .addMapping(Beacon::getCreatedDate, Beacon::setCreatedDate)
      .addMapping(Beacon::getEmergencyContacts, Beacon::setEmergencyContacts)
      .addMapping(Beacon::getHexId, Beacon::setHexId)
      .addMapping(Beacon::getLastServicedDate, Beacon::setLastServicedDate)
      .addMapping(Beacon::getManufacturer, Beacon::setManufacturer)
      .addMapping(
        Beacon::getManufacturerSerialNumber,
        Beacon::setManufacturerSerialNumber
      )
      .addMapping(Beacon::getModel, Beacon::setModel)
      .addMapping(Beacon::getReferenceNumber, Beacon::setReferenceNumber);

    var updatedModel = patcher.patchModel(beacon, update);

    beaconRepository.save(updatedModel);
  }
}
