package uk.gov.mca.beacons.api.services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;
import uk.gov.mca.beacons.api.gateways.UseGateway;
import uk.gov.mca.beacons.api.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.BeaconsRelationshipMapper;
import uk.gov.mca.beacons.api.mappers.ModelPatcherFactory;

@Service
@Transactional
public class BeaconsService {

  private final BeaconGateway beaconGateway;
  private final BeaconPersonJpaRepository beaconPersonJpaRepository;
  private final UseGateway useGateway;
  private final BeaconsRelationshipMapper beaconsRelationshipMapper;
  private final ModelPatcherFactory<Beacon> beaconPatcherFactory;

  @Autowired
  public BeaconsService(
    BeaconGateway beaconGateway,
    BeaconPersonJpaRepository beaconPersonJpaRepository,
    UseGateway useGateway,
    BeaconsRelationshipMapper beaconsRelationshipMapper,
    ModelPatcherFactory<Beacon> beaconPatcherFactory
  ) {
    this.beaconGateway = beaconGateway;
    this.beaconPersonJpaRepository = beaconPersonJpaRepository;
    this.useGateway = useGateway;
    this.beaconsRelationshipMapper = beaconsRelationshipMapper;
    this.beaconPatcherFactory = beaconPatcherFactory;
  }

  public Beacon find(UUID id) {
    final Beacon beacon = beaconGateway
      .findById(id)
      .orElseThrow(ResourceNotFoundException::new);

    final List<Person> persons = beaconPersonJpaRepository.findAllByBeaconId(
      id
    );
    final List<BeaconUse> beaconUses = useGateway.findAllByBeaconId(id);

    return beaconsRelationshipMapper.getMappedBeacon(
      beacon,
      persons,
      beaconUses
    );
  }

  public Beacon update(UUID id, Beacon update) {
    final Beacon beacon = this.find(id);

    final var patcher = beaconPatcherFactory
      .getModelPatcher()
      .withMapping(Beacon::getBatteryExpiryDate, Beacon::setBatteryExpiryDate)
      .withMapping(Beacon::getBeaconStatus, Beacon::setBeaconStatus)
      .withMapping(Beacon::getChkCode, Beacon::setChkCode)
      .withMapping(Beacon::getLastServicedDate, Beacon::setLastServicedDate)
      .withMapping(Beacon::getManufacturer, Beacon::setManufacturer)
      .withMapping(Beacon::getMti, Beacon::setMti)
      .withMapping(Beacon::getSvdr, Beacon::setSvdr)
      .withMapping(
        Beacon::getManufacturerSerialNumber,
        Beacon::setManufacturerSerialNumber
      )
      .withMapping(Beacon::getModel, Beacon::setModel);

    var updatedModel = patcher.patchModel(beacon, update);

    return beaconGateway.update(updatedModel);
  }
}
