package uk.gov.mca.beacons.api.beaconuse.mappers;

import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.beaconuse.domain.BeaconUse;
import uk.gov.mca.beacons.api.beaconuse.rest.BeaconUseDTO;
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

  public BeaconUseRegistrationDTO toBeaconRegistrationDTO(BeaconUse beaconUse) {
    BeaconUseRegistrationDTO dto = new BeaconUseRegistrationDTO();
    dto.setEnvironment(beaconUse.getEnvironment());
    dto.setPurpose(beaconUse.getPurpose());
    dto.setActivity(beaconUse.getActivity());
    dto.setOtherActivity(beaconUse.getOtherActivity());
    dto.setCallSign(beaconUse.getCallSign());
    dto.setVhfRadio(beaconUse.getVhfRadio());
    dto.setFixedVhfRadio(beaconUse.getFixedVhfRadio());
    dto.setFixedVhfRadioValue(beaconUse.getFixedVhfRadioValue());
    dto.setPortableVhfRadio(beaconUse.getPortableVhfRadio());
    dto.setPortableVhfRadioValue(beaconUse.getPortableVhfRadioValue());
    dto.setSatelliteTelephone(beaconUse.getSatelliteTelephone());
    dto.setSatelliteTelephoneValue(beaconUse.getSatelliteTelephoneValue());
    dto.setMobileTelephone(beaconUse.getMobileTelephone());
    dto.setMobileTelephone1(beaconUse.getMobileTelephone1());
    dto.setMobileTelephone2(beaconUse.getMobileTelephone2());
    dto.setOtherCommunication(beaconUse.getOtherCommunication());
    dto.setOtherCommunicationValue(beaconUse.getOtherCommunicationValue());
    dto.setMaxCapacity(beaconUse.getMaxCapacity());
    dto.setVesselName(beaconUse.getVesselName());
    dto.setPortLetterNumber(beaconUse.getPortLetterNumber());
    dto.setHomeport(beaconUse.getHomeport());
    dto.setAreaOfOperation(beaconUse.getAreaOfOperation());
    dto.setBeaconLocation(beaconUse.getBeaconLocation());
    dto.setImoNumber(beaconUse.getImoNumber());
    dto.setSsrNumber(beaconUse.getSsrNumber());
    dto.setRssNumber(beaconUse.getRssNumber());
    dto.setOfficialNumber(beaconUse.getOfficialNumber());
    dto.setRigPlatformLocation(beaconUse.getRigPlatformLocation());
    dto.setMainUse(beaconUse.getMainUse());
    dto.setAircraftManufacturer(beaconUse.getAircraftManufacturer());
    dto.setPrincipalAirport(beaconUse.getPrincipalAirport());
    dto.setSecondaryAirport(beaconUse.getSecondaryAirport());
    dto.setRegistrationMark(beaconUse.getRegistrationMark());
    dto.setHexAddress(beaconUse.getHexAddress());
    dto.setCnOrMsnNumber(beaconUse.getCnOrMsnNumber());
    dto.setDongle(beaconUse.getDongle());
    dto.setBeaconPosition(beaconUse.getBeaconPosition());
    dto.setWorkingRemotelyLocation(beaconUse.getWorkingRemotelyLocation());
    dto.setWorkingRemotelyPeopleCount(
      beaconUse.getWorkingRemotelyPeopleCount()
    );
    dto.setWindfarmLocation(beaconUse.getWindfarmLocation());
    dto.setWindfarmPeopleCount(beaconUse.getWindfarmPeopleCount());
    dto.setOtherActivityLocation(beaconUse.getOtherActivityLocation());
    dto.setOtherActivityPeopleCount(beaconUse.getOtherActivityPeopleCount());
    dto.setMoreDetails(beaconUse.getMoreDetails());

    return dto;
  }

  BeaconUseDTO toDTO(BeaconUse beaconUse) {
    BeaconUseDTO dto = new BeaconUseDTO();
    var attributes = BeaconUseDTO.Attributes
      .builder()
      .environment(beaconUse.getEnvironment())
      .purpose(beaconUse.getPurpose())
      .activity(beaconUse.getActivity())
      .otherActivity(beaconUse.getOtherActivity())
      .callSign(beaconUse.getCallSign())
      .vhfRadio(beaconUse.getVhfRadio())
      .fixedVhfRadio(beaconUse.getFixedVhfRadio())
      .fixedVhfRadioValue(beaconUse.getFixedVhfRadioValue())
      .portableVhfRadio(beaconUse.getPortableVhfRadio())
      .portableVhfRadioValue(beaconUse.getPortableVhfRadioValue())
      .satelliteTelephone(beaconUse.getSatelliteTelephone())
      .satelliteTelephoneValue(beaconUse.getSatelliteTelephoneValue())
      .mobileTelephone(beaconUse.getMobileTelephone())
      .mobileTelephone1(beaconUse.getMobileTelephone1())
      .mobileTelephone2(beaconUse.getMobileTelephone2())
      .otherCommunication(beaconUse.getOtherCommunication())
      .otherCommunicationValue(beaconUse.getOtherCommunicationValue())
      .maxCapacity(beaconUse.getMaxCapacity())
      .vesselName(beaconUse.getVesselName())
      .portLetterNumber(beaconUse.getPortLetterNumber())
      .homeport(beaconUse.getHomeport())
      .areaOfOperation(beaconUse.getAreaOfOperation())
      .beaconLocation(beaconUse.getBeaconLocation())
      .imoNumber(beaconUse.getImoNumber())
      .ssrNumber(beaconUse.getSsrNumber())
      .rssNumber(beaconUse.getRssNumber())
      .officialNumber(beaconUse.getOfficialNumber())
      .rigPlatformLocation(beaconUse.getRigPlatformLocation())
      .mainUse(beaconUse.getMainUse())
      .aircraftManufacturer(beaconUse.getAircraftManufacturer())
      .principalAirport(beaconUse.getPrincipalAirport())
      .secondaryAirport(beaconUse.getSecondaryAirport())
      .registrationMark(beaconUse.getRegistrationMark())
      .hexAddress(beaconUse.getHexAddress())
      .cnOrMsnNumber(beaconUse.getCnOrMsnNumber())
      .dongle(beaconUse.getDongle())
      .beaconPosition(beaconUse.getBeaconPosition())
      .workingRemotelyLocation(beaconUse.getWorkingRemotelyLocation())
      .workingRemotelyPeopleCount(beaconUse.getWorkingRemotelyPeopleCount())
      .windfarmLocation(beaconUse.getWindfarmLocation())
      .windfarmPeopleCount(beaconUse.getWindfarmPeopleCount())
      .otherActivityLocation(beaconUse.getOtherActivityLocation())
      .otherActivityPeopleCount(beaconUse.getOtherActivityPeopleCount())
      .moreDetails(beaconUse.getMoreDetails())
      .build();

    dto.setAttributes(attributes);
    return dto;
  }
}
