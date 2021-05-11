package uk.gov.mca.beacons.service.beacons;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.mappers.BeaconMapper;
import uk.gov.mca.beacons.service.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.service.mappers.BeaconUseMapper;
import uk.gov.mca.beacons.service.mappers.RelationshipMapper;
import uk.gov.mca.beacons.service.model.Beacon;

@Service
public class BeaconsResponseFactory {

  private final BeaconMapper beaconMapper;
  private final BeaconPersonMapper personMapper;
  private final BeaconUseMapper useMapper;
  private final RelationshipMapper<BeaconPersonDTO> personRelationshipMapper;
  private final RelationshipMapper<BeaconUseDTO> useRelationshipMapper;

  @Autowired
  public BeaconsResponseFactory(
    BeaconMapper beaconMapper,
    BeaconPersonMapper personMapper,
    BeaconUseMapper useMapper,
    RelationshipMapper<BeaconPersonDTO> personRelationshipMapper,
    RelationshipMapper<BeaconUseDTO> useRelationshipMapper
  ) {
    this.beaconMapper = beaconMapper;
    this.personMapper = personMapper;
    this.useMapper = useMapper;
    this.personRelationshipMapper = personRelationshipMapper;
    this.useRelationshipMapper = useRelationshipMapper;
  }

  public WrapperDTO<BeaconDTO> buildDTO(Beacon beacon) {
    final var useDTOs = getUseDTOs(beacon);
    final var ownerDTO = getOwnerDTO(beacon);
    final var emergencyContactDTOs = getEmergencyContactDTOs(beacon);

    final var beaconDTO = buildBeaconDTO(
      beacon,
      useDTOs,
      ownerDTO,
      emergencyContactDTOs
    );

    final var wrapper = getWrappedDTO(
      beaconDTO,
      useDTOs,
      ownerDTO,
      emergencyContactDTOs
    );

    return wrapper;
  }

  private List<BeaconUseDTO> getUseDTOs(Beacon beacon) {
    return beacon
      .getUses()
      .stream()
      .map(beaconUse -> useMapper.toDTO(beaconUse))
      .collect(Collectors.toList());
  }

  private BeaconPersonDTO getOwnerDTO(Beacon beacon) {
    if (beacon.getOwner() != null) {
      final var ownerDTO = personMapper.toDTO(beacon.getOwner());
      return ownerDTO;
    }
    return null;
  }

  private List<BeaconPersonDTO> getEmergencyContactDTOs(Beacon beacon) {
    return beacon
      .getEmergencyContacts()
      .stream()
      .map(emergencyContact -> personMapper.toDTO(emergencyContact))
      .collect(Collectors.toList());
  }

  private BeaconDTO buildBeaconDTO(
    Beacon beacon,
    List<BeaconUseDTO> useDTOs,
    BeaconPersonDTO ownerDTO,
    List<BeaconPersonDTO> emergencyContactDTOs
  ) {
    final var beaconDTO = beaconMapper.toDTO(beacon);
    final var useRelationshipDTO = useRelationshipMapper.toDTO(useDTOs);
    final var ownerRelationshipDTO = personRelationshipMapper.toDTO(ownerDTO);
    final var emergencyContactRelationshipDTO = personRelationshipMapper.toDTO(
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
    final var wrapper = new WrapperDTO<BeaconDTO>();

    wrapper.setData(beaconDTO);
    useDTOs.forEach(wrapper::addIncluded);
    wrapper.addIncluded(ownerDTO);
    emergencyContactDTOs.forEach(wrapper::addIncluded);

    return wrapper;
  }
}
