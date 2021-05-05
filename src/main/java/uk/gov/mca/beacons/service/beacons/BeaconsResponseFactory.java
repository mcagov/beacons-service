package uk.gov.mca.beacons.service.beacons;

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
    List<DomainDTO> useDTOs = getUseDTOs(beacon);
    BeaconPersonDTO ownerDTO = getOwnerDTO(beacon);
    List<DomainDTO> emergencyContactDTOs = getEmergencyContactDTOs(beacon);

    BeaconDTO beaconDTO = buildBeaconDTO(
      beacon,
      useDTOs,
      ownerDTO,
      emergencyContactDTOs
    );

    WrapperDTO<BeaconDTO> wrapper = getWrappedDTO(
      beaconDTO,
      useDTOs,
      ownerDTO,
      emergencyContactDTOs
    );

    return wrapper;
  }

  private static List<DomainDTO> getUseDTOs(Beacon beacon) {
    return beacon
      .getUses()
      .stream()
      .map(beaconUse -> BeaconUseDTO.from(beaconUse))
      .collect(Collectors.toList());
  }

  private static BeaconPersonDTO getOwnerDTO(Beacon beacon) {
    if (beacon.getOwner() != null) {
      var ownerDTO = BeaconPersonDTO.from(beacon.getOwner());
      return ownerDTO;
    }
    return null;
  }

  private static List<DomainDTO> getEmergencyContactDTOs(Beacon beacon) {
    return beacon
      .getEmergencyContacts()
      .stream()
      .map(emergencyContact -> BeaconPersonDTO.from(emergencyContact))
      .collect(Collectors.toList());
  }

  private static BeaconDTO buildBeaconDTO(
    Beacon beacon,
    List<DomainDTO> useDTOs,
    BeaconPersonDTO ownerDTO,
    List<DomainDTO> emergencyContactDTOs
  ) {
    var beaconDTO = BeaconDTO.from(beacon);
    var useRelationshipDTO = RelationshipDTO.from(useDTOs);
    var ownerRelationshipDTO = RelationshipDTO.from(ownerDTO);
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
    BeaconPersonDTO ownerDTO,
    List<DomainDTO> emergencyContactDTOs
  ) {
    WrapperDTO<BeaconDTO> wrapper = getWrapperDTO(beaconDTO);

    useDTOs.forEach(wrapper::addIncluded);
    wrapper.addIncluded(ownerDTO);
    emergencyContactDTOs.forEach(wrapper::addIncluded);
    return wrapper;
  }

  private static WrapperDTO<BeaconDTO> getWrapperDTO(BeaconDTO beaconDTO) {
    var wrapper = new WrapperDTO<BeaconDTO>();
    wrapper.addData(beaconDTO);
    return wrapper;
  }
}
