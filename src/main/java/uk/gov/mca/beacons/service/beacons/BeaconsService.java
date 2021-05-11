package uk.gov.mca.beacons.service.beacons;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    final Optional<Beacon> beaconResult = beaconRepository.findById(id);
    if (beaconResult.isEmpty()) 
      throw new RuntimeException(); //TODO: pick an exception

    final Beacon beacon = beaconResult.get();

    updateIfNotNull(beacon, update, Beacon::getBatteryExpiryDate, Beacon::setBatteryExpiryDate);
    updateIfNotNull(beacon, update, Beacon::getBeaconStatus, Beacon::setBeaconStatus);
    updateIfNotNull(beacon, update, Beacon::getChkCode, Beacon::setChkCode);
    updateIfNotNull(beacon, update, Beacon::getCreatedDate, Beacon::setCreatedDate);
    updateIfNotNull(beacon, update, Beacon::getEmergencyContacts, Beacon::setEmergencyContacts);
    updateIfNotNull(beacon, update, Beacon::getHexId, Beacon::setHexId);
    updateIfNotNull(beacon, update, Beacon::getLastServicedDate, Beacon::setLastServicedDate);
    updateIfNotNull(beacon, update, Beacon::getManufacturer, Beacon::setManufacturer);
    updateIfNotNull(beacon, update, Beacon::getManufacturerSerialNumber, Beacon::setManufacturerSerialNumber);
    updateIfNotNull(beacon, update, Beacon::getModel, Beacon::setModel);
    updateIfNotNull(beacon, update, Beacon::getReferenceNumber, Beacon::setReferenceNumber);

    beaconRepository.save(beacon);
  }

  private <TModel, T> void updateIfNotNull(TModel beacon, TModel update, Function<TModel, T> get, BiConsumer<TModel, T> set) {
    T updateValue = get.apply(update);
    if(updateValue==null)
      return;
    
    set.accept(beacon, updateValue);
  }

}
