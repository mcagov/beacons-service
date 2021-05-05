package uk.gov.mca.beacons.service.beacons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.DomainDTO;
import uk.gov.mca.beacons.service.dto.RelationshipDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.Beacon;

public class BeaconsResponseFactory {

  public static WrapperDTO<BeaconDTO> buildDTO(Beacon beacon) {
    List<DomainDTO> useDTOs = getUseDTOS(beacon);
    List<DomainDTO> ownerDTOs = getOwnerDTOS(beacon);
    List<DomainDTO> emergencyContactDTOs = getEmergencyContactDTOS(beacon);

    BeaconDTO beaconDTO = buildBeaconDTO(
      beacon,
      useDTOs,
      ownerDTOs,
      emergencyContactDTOs
    );

    WrapperDTO<BeaconDTO> wrapper = getWrappedDTO(
      beaconDTO,
      useDTOs,
      ownerDTOs,
      emergencyContactDTOs
    );

    return wrapper;
  }

  private static List<DomainDTO> getUseDTOS(Beacon beacon) {
    return beacon
      .getUses()
      .stream()
      .map(u -> BeaconUseDTO.from(u))
      .collect(Collectors.toList());
  }

  private static List<DomainDTO> getOwnerDTOS(Beacon beacon) {
    List<DomainDTO> ownerDTOs = new ArrayList<DomainDTO>();
    if (beacon.getOwner() != null) {
      DomainDTO ownerDTO = BeaconPersonDTO.from(beacon.getOwner());
      ownerDTOs.add(ownerDTO);
    }
    return ownerDTOs;
  }

  private static List<DomainDTO> getEmergencyContactDTOS(Beacon beacon) {
    return beacon
      .getEmergencyContacts()
      .stream()
      .map(emergencyContact -> BeaconPersonDTO.from(emergencyContact))
      .collect(Collectors.toList());
  }

  private static WrapperDTO<BeaconDTO> getWrapperDTO(BeaconDTO beaconDTO) {
    var wrapper = new WrapperDTO<BeaconDTO>();
    wrapper.addData(beaconDTO);
    return wrapper;
  }

  private static BeaconDTO buildBeaconDTO(
    Beacon beacon,
    List<DomainDTO> useDTOs,
    List<DomainDTO> ownerDTOs,
    List<DomainDTO> emergencyContactDTOs
  ) {
    var beaconDTO = BeaconDTO.from(beacon);
    var useRelationshipDTO = RelationshipDTO.from(useDTOs);
    var ownerRelationshipDTO = RelationshipDTO.from(ownerDTOs);
    var emergencyContactRelationshipDTO = RelationshipDTO.from(
      emergencyContactDTOs
    );

    beaconDTO.addRelationship("uses", useRelationshipDTO);
    beaconDTO.addRelationship("owner", ownerRelationshipDTO);
    beaconDTO.addRelationship(
      "emergencyContacts",
      emergencyContactRelationshipDTO
    );
    return beaconDTO;
  }

  private static WrapperDTO<BeaconDTO> getWrappedDTO(
    BeaconDTO beaconDTO,
    List<DomainDTO> useDTOs,
    List<DomainDTO> ownerDTOs,
    List<DomainDTO> emergencyContactDTOs
  ) {
    WrapperDTO<BeaconDTO> wrapper = getWrapperDTO(beaconDTO);

    useDTOs.forEach(wrapper::addIncluded);
    ownerDTOs.forEach(wrapper::addIncluded);
    emergencyContactDTOs.forEach(wrapper::addIncluded);
    return wrapper;
  }
}
