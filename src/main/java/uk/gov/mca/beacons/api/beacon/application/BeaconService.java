package uk.gov.mca.beacons.api.beacon.application;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.beacon.domain.BeaconId;
import uk.gov.mca.beacons.api.beacon.domain.BeaconRepository;
import uk.gov.mca.beacons.api.beacon.domain.BeaconStatus;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.mappers.ModelPatcherFactory;

@Transactional
@Service("BeaconServiceV2")
public class BeaconService {

  private final BeaconRepository beaconRepository;
  private final ModelPatcherFactory<Beacon> beaconModelPatcherFactory;

  @Autowired
  BeaconService(
    BeaconRepository beaconRepository,
    ModelPatcherFactory<Beacon> beaconModelPatcherFactory
  ) {
    this.beaconRepository = beaconRepository;
    this.beaconModelPatcherFactory = beaconModelPatcherFactory;
  }

  public Beacon create(Beacon beacon) {
    return beaconRepository.save(beacon);
  }

  public Optional<Beacon> findById(BeaconId beacon) {
    return beaconRepository.findById(beacon);
  }

  public List<Beacon> getByAccountHolderId(AccountHolderId accountHolderId) {
    return beaconRepository.getByAccountHolderId(accountHolderId);
  }

  public List<Beacon> getByAccountHolderIdWhereStatusIsNew(
    AccountHolderId accountHolderId
  ) {
    return beaconRepository.getByAccountHolderIdAndBeaconStatus(
      accountHolderId,
      BeaconStatus.NEW
    );
  }

  public Beacon update(BeaconId beaconId, Beacon beaconUpdate)
    throws ResourceNotFoundException {
    Beacon toUpdate = beaconRepository
      .findById(beaconId)
      .orElseThrow(ResourceNotFoundException::new);

    final var patcher = beaconModelPatcherFactory
      .getModelPatcher()
      .withMapping(Beacon::getBatteryExpiryDate, Beacon::setBatteryExpiryDate)
      .withMapping(Beacon::getBeaconStatus, Beacon::setBeaconStatus)
      .withMapping(Beacon::getChkCode, Beacon::setChkCode)
      .withMapping(Beacon::getLastServicedDate, Beacon::setLastServicedDate)
      .withMapping(Beacon::getManufacturer, Beacon::setManufacturer)
      .withMapping(Beacon::getMti, Beacon::setMti)
      .withMapping(Beacon::getSvdr, Beacon::setSvdr)
      .withMapping(Beacon::getCsta, Beacon::setCsta)
      .withMapping(Beacon::getBeaconType, Beacon::setBeaconType)
      .withMapping(Beacon::getProtocol, Beacon::setProtocol)
      .withMapping(Beacon::getCoding, Beacon::setCoding)
      .withMapping(
        Beacon::getManufacturerSerialNumber,
        Beacon::setManufacturerSerialNumber
      )
      .withMapping(Beacon::getModel, Beacon::setModel);

    Beacon updatedBeacon = patcher.patchModel(toUpdate, beaconUpdate);

    return beaconRepository.save(updatedBeacon);
  }
}