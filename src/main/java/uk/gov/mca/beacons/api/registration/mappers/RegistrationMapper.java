package uk.gov.mca.beacons.api.registration.mappers;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.beacon.mappers.BeaconMapper;
import uk.gov.mca.beacons.api.beaconowner.mappers.BeaconOwnerMapper;
import uk.gov.mca.beacons.api.beaconuse.mappers.BeaconUseMapper;
import uk.gov.mca.beacons.api.emergencycontact.mappers.EmergencyContactMapper;
import uk.gov.mca.beacons.api.registration.domain.Registration;
import uk.gov.mca.beacons.api.registration.rest.RegistrationDTO;

@Component("RegistrationMapperV2")
public class RegistrationMapper {

  private final BeaconMapper beaconMapper;
  private final BeaconUseMapper beaconUseMapper;
  private final BeaconOwnerMapper beaconOwnerMapper;
  private final EmergencyContactMapper emergencyContactMapper;

  @Autowired
  public RegistrationMapper(
    BeaconMapper beaconMapper,
    BeaconUseMapper beaconUseMapper,
    BeaconOwnerMapper beaconOwnerMapper,
    EmergencyContactMapper emergencyContactMapper
  ) {
    this.beaconMapper = beaconMapper;
    this.beaconUseMapper = beaconUseMapper;
    this.beaconOwnerMapper = beaconOwnerMapper;
    this.emergencyContactMapper = emergencyContactMapper;
  }

  public Registration fromDTO(RegistrationDTO dto) {
    Registration registration = new Registration();
    registration.setBeacon(beaconMapper.fromDTO(dto.getCreateBeaconDTO()));
    registration.setBeaconUses(
      dto
        .getCreateBeaconUseDTOs()
        .stream()
        .map(beaconUseMapper::fromDTO)
        .collect(Collectors.toList())
    );
    registration.setBeaconOwner(
      beaconOwnerMapper.fromDTO(dto.getCreateBeaconOwnerDTO())
    );
    registration.setEmergencyContacts(
      dto
        .getCreateEmergencyContactDTOs()
        .stream()
        .map(emergencyContactMapper::fromDTO)
        .collect(Collectors.toList())
    );

    return registration;
  }
}
