package uk.gov.mca.beacons.service.beacons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.RelationshipDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.Beacon;

public class BeaconsResponseFactory {

  public static WrapperDTO<BeaconDTO> buildDTO(Beacon beacon) {
    List<BeaconUseDTO> useDTOs = getUseDTOs(beacon);
    BeaconPersonDTO ownerDTO = getOwnerDTO(beacon);
    List<BeaconPersonDTO> emergencyContactDTOs = getEmergencyContactDTOs(
      beacon
    );

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

  private static List<BeaconUseDTO> getUseDTOs(Beacon beacon) {
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

  private static List<BeaconPersonDTO> getEmergencyContactDTOs(Beacon beacon) {
    return beacon
      .getEmergencyContacts()
      .stream()
      .map(emergencyContact -> BeaconPersonDTO.from(emergencyContact))
      .collect(Collectors.toList());
  }

  private static BeaconDTO buildBeaconDTO(
    Beacon beacon,
    List<BeaconUseDTO> useDTOs,
    BeaconPersonDTO ownerDTO,
    List<BeaconPersonDTO> emergencyContactDTOs
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
    List<BeaconUseDTO> useDTOs,
    BeaconPersonDTO ownerDTO,
    List<BeaconPersonDTO> emergencyContactDTOs
  ) {
    return getWrappedDTO(
      List.of(beaconDTO),
      useDTOs,
      List.of(ownerDTO),
      emergencyContactDTOs
    );
  }

  private static WrapperDTO<BeaconDTO> getWrappedDTO(
    List<BeaconDTO> beaconDTOs,
    List<BeaconUseDTO> useDTOs,
    List<BeaconPersonDTO> ownerDTOs,
    List<BeaconPersonDTO> emergencyContactDTOs
  ) {
    var wrapper = new WrapperDTO<BeaconDTO>();

    beaconDTOs.forEach(wrapper::addData);

    useDTOs.forEach(wrapper::addIncluded);
    ownerDTOs.forEach(wrapper::addIncluded);
    emergencyContactDTOs.forEach(wrapper::addIncluded);

    return wrapper;
  }
}
