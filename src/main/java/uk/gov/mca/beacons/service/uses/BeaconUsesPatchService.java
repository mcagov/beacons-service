package uk.gov.mca.beacons.service.uses;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.mappers.ModelPatcher;
import uk.gov.mca.beacons.service.mappers.ModelPatcherFactory;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Service
public class BeaconUsesPatchService {

  private final BeaconUseRepository beaconUseRepository;
  private final ModelPatcherFactory<BeaconUse> beaconUsePatcherFactory;

  @Autowired
  public BeaconUsesPatchService(
    BeaconUseRepository beaconUseRepository,
    ModelPatcherFactory<BeaconUse> beaconUsePatcherFactory
  ) {
    this.beaconUseRepository = beaconUseRepository;
    this.beaconUsePatcherFactory = beaconUsePatcherFactory;
  }

  public void update(UUID id, BeaconUse beaconUseToUpdateWith) {
    final var currentBeaconUse = beaconUseRepository.findById(id);
    if (currentBeaconUse.isEmpty()) throw new ResourceNotFoundException();

    final var patcher = getBeaconUsePatcher();
    final var update = patcher.patchModel(
      currentBeaconUse.get(),
      beaconUseToUpdateWith
    );

    beaconUseRepository.save(update);
  }

  private ModelPatcher<BeaconUse> getBeaconUsePatcher() {
    return beaconUsePatcherFactory
      .getModelPatcher()
      .withMapping(BeaconUse::getEnvironment, BeaconUse::setEnvironment)
      .withMapping(BeaconUse::getPurpose, BeaconUse::setPurpose)
      .withMapping(BeaconUse::getActivity, BeaconUse::setActivity)
      .withMapping(BeaconUse::getOtherActivity, BeaconUse::setOtherActivity)
      .withMapping(BeaconUse::getCallSign, BeaconUse::setCallSign)
      .withMapping(BeaconUse::getVhfRadio, BeaconUse::setVhfRadio)
      .withMapping(BeaconUse::getFixedVhfRadio, BeaconUse::setFixedVhfRadio)
      .withMapping(
        BeaconUse::getFixedVhfRadioValue,
        BeaconUse::setFixedVhfRadioValue
      )
      .withMapping(
        BeaconUse::getPortableVhfRadio,
        BeaconUse::setPortableVhfRadio
      )
      .withMapping(
        BeaconUse::getPortableVhfRadioValue,
        BeaconUse::setPortableVhfRadioValue
      )
      .withMapping(
        BeaconUse::getSatelliteTelephone,
        BeaconUse::setSatelliteTelephone
      )
      .withMapping(
        BeaconUse::getSatelliteTelephoneValue,
        BeaconUse::setSatelliteTelephoneValue
      )
      .withMapping(BeaconUse::getMobileTelephone, BeaconUse::setMobileTelephone)
      .withMapping(
        BeaconUse::getMobileTelephone1,
        BeaconUse::setMobileTelephone1
      )
      .withMapping(
        BeaconUse::getMobileTelephone2,
        BeaconUse::setMobileTelephone2
      )
      .withMapping(
        BeaconUse::getOtherCommunication,
        BeaconUse::setOtherCommunication
      )
      .withMapping(
        BeaconUse::getOtherCommunicationValue,
        BeaconUse::setOtherCommunicationValue
      )
      .withMapping(BeaconUse::getMaxCapacity, BeaconUse::setMaxCapacity)
      .withMapping(BeaconUse::getVesselName, BeaconUse::setVesselName)
      .withMapping(
        BeaconUse::getPortLetterNumber,
        BeaconUse::setPortLetterNumber
      )
      .withMapping(BeaconUse::getHomeport, BeaconUse::setHomeport)
      .withMapping(BeaconUse::getAreaOfOperation, BeaconUse::setAreaOfOperation)
      .withMapping(BeaconUse::getBeaconLocation, BeaconUse::setBeaconLocation)
      .withMapping(BeaconUse::getImoNumber, BeaconUse::setImoNumber)
      .withMapping(BeaconUse::getSsrNumber, BeaconUse::setSsrNumber)
      .withMapping(BeaconUse::getRssNumber, BeaconUse::setRssNumber)
      .withMapping(BeaconUse::getOfficialNumber, BeaconUse::setOfficialNumber)
      .withMapping(
        BeaconUse::getRigPlatformLocation,
        BeaconUse::setRigPlatformLocation
      )
      .withMapping(BeaconUse::getMainUse, BeaconUse::setMainUse)
      .withMapping(
        BeaconUse::getAircraftManufacturer,
        BeaconUse::setAircraftManufacturer
      )
      .withMapping(
        BeaconUse::getPrincipalAirport,
        BeaconUse::setPrincipalAirport
      )
      .withMapping(
        BeaconUse::getSecondaryAirport,
        BeaconUse::setSecondaryAirport
      )
      .withMapping(
        BeaconUse::getRegistrationMark,
        BeaconUse::setRegistrationMark
      )
      .withMapping(BeaconUse::getHexAddress, BeaconUse::setHexAddress)
      .withMapping(BeaconUse::getCnOrMsnNumber, BeaconUse::setCnOrMsnNumber)
      .withMapping(BeaconUse::getDongle, BeaconUse::setDongle)
      .withMapping(BeaconUse::getBeaconPosition, BeaconUse::setBeaconPosition)
      .withMapping(
        BeaconUse::getWorkingRemotelyLocation,
        BeaconUse::setWorkingRemotelyLocation
      )
      .withMapping(
        BeaconUse::getWorkingRemotelyPeopleCount,
        BeaconUse::setWorkingRemotelyPeopleCount
      )
      .withMapping(
        BeaconUse::getWindfarmLocation,
        BeaconUse::setWindfarmLocation
      )
      .withMapping(
        BeaconUse::getWindfarmPeopleCount,
        BeaconUse::setWindfarmPeopleCount
      )
      .withMapping(
        BeaconUse::getOtherActivityLocation,
        BeaconUse::setOtherActivityLocation
      )
      .withMapping(
        BeaconUse::getOtherActivityPeopleCount,
        BeaconUse::setOtherActivityPeopleCount
      )
      .withMapping(BeaconUse::getMoreDetails, BeaconUse::setMoreDetails)
      .withMapping(BeaconUse::getCreatedDate, BeaconUse::setCreatedDate);
  }
}
