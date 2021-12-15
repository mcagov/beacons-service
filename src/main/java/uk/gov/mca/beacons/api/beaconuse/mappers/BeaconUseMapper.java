package uk.gov.mca.beacons.api.beaconuse.mappers;

import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.beaconuse.domain.BeaconUse;
import uk.gov.mca.beacons.api.beaconuse.rest.BeaconUseRegistrationDTO;

@Component("BeaconUseMapperV2")
public class BeaconUseMapper {

  public BeaconUse fromDTO(BeaconUseRegistrationDTO dto) {
    BeaconUse beaconUse = new BeaconUse();
    beaconUse.setEnvironment(dto.getEnvironment());
    beaconUse.setPurpose(dto.getPurpose());
    beaconUse.setActivity(dto.getActivity());
    beaconUse.setOtherActivity(dto.getOtherActivity());
    beaconUse.setCallSign(dto.getCallSign());
    beaconUse.setVhfRadio(dto.getVhfRadio());
    beaconUse.setFixedVhfRadio(dto.getFixedVhfRadio());
    beaconUse.setFixedVhfRadioValue(dto.getFixedVhfRadioValue());
    beaconUse.setPortableVhfRadio(dto.getPortableVhfRadio());
    beaconUse.setPortableVhfRadioValue(dto.getPortableVhfRadioValue());
    beaconUse.setSatelliteTelephone(dto.getSatelliteTelephone());
    beaconUse.setSatelliteTelephoneValue(dto.getSatelliteTelephoneValue());
    beaconUse.setMobileTelephone(dto.getMobileTelephone());
    beaconUse.setMobileTelephone1(dto.getMobileTelephone1());
    beaconUse.setMobileTelephone2(dto.getMobileTelephone2());
    beaconUse.setOtherCommunication(dto.getOtherCommunication());
    beaconUse.setOtherCommunicationValue(dto.getOtherCommunicationValue());
    beaconUse.setMaxCapacity(dto.getMaxCapacity());
    beaconUse.setVesselName(dto.getVesselName());
    beaconUse.setPortLetterNumber(dto.getPortLetterNumber());
    beaconUse.setHomeport(dto.getHomeport());
    beaconUse.setAreaOfOperation(dto.getAreaOfOperation());
    beaconUse.setBeaconLocation(dto.getBeaconLocation());
    beaconUse.setImoNumber(dto.getImoNumber());
    beaconUse.setSsrNumber(dto.getSsrNumber());
    beaconUse.setRssNumber(dto.getRssNumber());
    beaconUse.setOfficialNumber(dto.getOfficialNumber());
    beaconUse.setRigPlatformLocation(dto.getRigPlatformLocation());
    beaconUse.setMainUse(dto.getMainUse());
    beaconUse.setAircraftManufacturer(dto.getAircraftManufacturer());
    beaconUse.setPrincipalAirport(dto.getPrincipalAirport());
    beaconUse.setSecondaryAirport(dto.getSecondaryAirport());
    beaconUse.setRegistrationMark(dto.getRegistrationMark());
    beaconUse.setHexAddress(dto.getHexAddress());
    beaconUse.setCnOrMsnNumber(dto.getCnOrMsnNumber());
    beaconUse.setDongle(dto.getDongle());
    beaconUse.setBeaconPosition(dto.getBeaconPosition());
    beaconUse.setWorkingRemotelyLocation(dto.getWorkingRemotelyLocation());
    beaconUse.setWorkingRemotelyPeopleCount(
      dto.getWorkingRemotelyPeopleCount()
    );
    beaconUse.setWindfarmLocation(dto.getWindfarmLocation());
    beaconUse.setWindfarmPeopleCount(dto.getWindfarmPeopleCount());
    beaconUse.setOtherActivityLocation(dto.getOtherActivityLocation());
    beaconUse.setOtherActivityPeopleCount(dto.getOtherActivityPeopleCount());
    beaconUse.setMoreDetails(dto.getMoreDetails());

    return beaconUse;
  }
}
