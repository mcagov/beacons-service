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
    final var patcher = new ModelPatcher<Beacon>(beacon, update);

    patcher.patchModel(Beacon::getBatteryExpiryDate, Beacon::setBatteryExpiryDate);
    patcher.patchModel(Beacon::getBeaconStatus, Beacon::setBeaconStatus);
    patcher.patchModel(Beacon::getChkCode, Beacon::setChkCode);
    patcher.patchModel(Beacon::getCreatedDate, Beacon::setCreatedDate);
    patcher.patchModel(Beacon::getEmergencyContacts, Beacon::setEmergencyContacts);
    patcher.patchModel(Beacon::getHexId, Beacon::setHexId);
    patcher.patchModel(Beacon::getLastServicedDate, Beacon::setLastServicedDate);
    patcher.patchModel(Beacon::getManufacturer, Beacon::setManufacturer);
    patcher.patchModel(Beacon::getManufacturerSerialNumber, Beacon::setManufacturerSerialNumber);
    patcher.patchModel(Beacon::getModel, Beacon::setModel);
    patcher.patchModel(Beacon::getReferenceNumber, Beacon::setReferenceNumber);

    beaconRepository.save(beacon);
  }

  

  public class ModelPatcher<T>{

    private T model;
    private T update;

    public ModelPatcher(T model, T update) {
      super();
      this.model = model;
      this.update = update;
    }

    public <TValue> void patchModel( Function<T, TValue> get, BiConsumer<T, TValue> set) {
    TValue updateValue = get.apply(update);
    if(updateValue==null)
      return;
    
    set.accept(model, updateValue);
  }
  }

}
