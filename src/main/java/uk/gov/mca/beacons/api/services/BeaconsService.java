package uk.gov.mca.beacons.api.services;

import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.BeaconSearchResultDTO;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.jpa.LegacyBeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.BeaconSearchResultMapper;
import uk.gov.mca.beacons.api.mappers.BeaconsRelationshipMapper;
import uk.gov.mca.beacons.api.mappers.ModelPatcherFactory;

@Service
@Transactional
public class BeaconsService {

  private final BeaconJpaRepository beaconJpaRepository;
  private final BeaconPersonJpaRepository beaconPersonJpaRepository;
  private final BeaconUseJpaRepository beaconUseJpaRepository;
  private final BeaconsRelationshipMapper beaconsRelationshipMapper;
  private final BeaconSearchResultMapper beaconSearchResultMapper;
  private final ModelPatcherFactory<Beacon> beaconPatcherFactory;

  @Autowired
  public BeaconsService(
    BeaconJpaRepository beaconJpaRepository,
    BeaconPersonJpaRepository beaconPersonJpaRepository,
    BeaconUseJpaRepository beaconUseJpaRepository,
    BeaconsRelationshipMapper beaconsRelationshipMapper,
    BeaconSearchResultMapper beaconSearchResultMapper,
    ModelPatcherFactory<Beacon> beaconPatcherFactory
  ) {
    this.beaconJpaRepository = beaconJpaRepository;
    this.beaconPersonJpaRepository = beaconPersonJpaRepository;
    this.beaconUseJpaRepository = beaconUseJpaRepository;
    this.beaconsRelationshipMapper = beaconsRelationshipMapper;
    this.beaconSearchResultMapper = beaconSearchResultMapper;
    this.beaconPatcherFactory = beaconPatcherFactory;
  }

  public List<BeaconSearchResultDTO> findAllBeaconSearchResult() {
    final List<Beacon> allBeacons = findAll();
    return beaconSearchResultMapper.getBeaconSearchResults(allBeacons);
  }

  public List<Beacon> findAll() {
    final List<Beacon> allBeacons = beaconJpaRepository.findAll();
    if (allBeacons.size() == 0) return emptyList();

    final List<Person> allPersons = beaconPersonJpaRepository.findAll();
    final List<BeaconUse> allBeaconUses = beaconUseJpaRepository.findAll();

    return beaconsRelationshipMapper.getMappedBeacons(
      allBeacons,
      allPersons,
      allBeaconUses
    );
  }

  public Beacon find(UUID id) {
    final Optional<Beacon> beaconResult = beaconJpaRepository.findById(id);
    if (beaconResult.isEmpty()) return null;

    final Beacon beacon = beaconResult.get();
    final List<Person> persons = beaconPersonJpaRepository.findAllByBeaconId(
      id
    );
    final List<BeaconUse> beaconUses = beaconUseJpaRepository.findAllByBeaconId(
      id
    );

    return beaconsRelationshipMapper.getMappedBeacon(
      beacon,
      persons,
      beaconUses
    );
  }

  public void update(UUID id, Beacon update) {
    final Beacon beacon = this.find(id);
    if (beacon == null) throw new ResourceNotFoundException();

    final var patcher = beaconPatcherFactory
      .getModelPatcher()
      .withMapping(Beacon::getBatteryExpiryDate, Beacon::setBatteryExpiryDate)
      .withMapping(Beacon::getBeaconStatus, Beacon::setBeaconStatus)
      .withMapping(Beacon::getChkCode, Beacon::setChkCode)
      .withMapping(Beacon::getCreatedDate, Beacon::setCreatedDate)
      .withMapping(Beacon::getHexId, Beacon::setHexId)
      .withMapping(Beacon::getLastServicedDate, Beacon::setLastServicedDate)
      .withMapping(Beacon::getManufacturer, Beacon::setManufacturer)
      .withMapping(
        Beacon::getManufacturerSerialNumber,
        Beacon::setManufacturerSerialNumber
      )
      .withMapping(Beacon::getModel, Beacon::setModel)
      .withMapping(Beacon::getReferenceNumber, Beacon::setReferenceNumber);

    var updatedModel = patcher.patchModel(beacon, update);

    beaconJpaRepository.save(updatedModel);
  }
}
