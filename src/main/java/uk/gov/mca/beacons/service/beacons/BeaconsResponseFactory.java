package uk.gov.mca.beacons.service.beacons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.DomainDTO;
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
  private final RelationshipMapper personRelationshipMapper;
  private final RelationshipMapper useRelationshipMapper;

  @Autowired
  public BeaconsResponseFactory(
    BeaconMapper beaconMapper,
    BeaconPersonMapper personMapper,
    BeaconUseMapper useMapper,
    RelationshipMapper personRelationshipMapper,
    RelationshipMapper useRelationshipMapper
  ) {
    this.beaconMapper = beaconMapper;
    this.personMapper = personMapper;
    this.useMapper = useMapper;
    this.personRelationshipMapper = personRelationshipMapper;
    this.useRelationshipMapper = useRelationshipMapper;
  }

  private WrapperDTO<List<BeaconDTO>> buildDTO(List<Beacon> beacons) {
    final var wrapper = new WrapperDTO<List<BeaconDTO>>();
    final List<BeaconDTO> beaconDTOs = new ArrayList<>();

    beacons.forEach(
      beacon -> {
        final var beaconDTO = getBeaconDTO(beacon);
        final var useDTOs = getUseDTOs(beacon);
        final var ownerDTO = getOwnerDTO(beacon);
        final var emergencyContactDTOs = getEmergencyContactDTOs(beacon);

        useDTOs.forEach(wrapper::addIncluded);
        wrapper.addIncluded(ownerDTO);
        emergencyContactDTOs.forEach(wrapper::addIncluded);
        beaconDTOs.add(beaconDTO);
      }
    );

    wrapper.setData(beaconDTOs);

    return wrapper;
  }

  public WrapperDTO<BeaconDTO> buildDTO(Beacon beacon) {
    final var beaconDTO = getBeaconDTO(beacon);
    final var useDTOs = getUseDTOs(beacon);
    final var ownerDTO = getOwnerDTO(beacon);
    final var emergencyContactDTOs = getEmergencyContactDTOs(beacon);

    return getWrappedDTO(beaconDTO, useDTOs, ownerDTO, emergencyContactDTOs);
  }

  private BeaconDTO getBeaconDTO(Beacon beacon) {
    final var useDTOs = getUseDTOs(beacon);
    final var ownerDTO = getOwnerDTO(beacon);
    final var emergencyContactDTOs = getEmergencyContactDTOs(beacon);

    return buildBeaconDTO(beacon, useDTOs, ownerDTO, emergencyContactDTOs);
  }

  private List<BeaconUseDTO> getUseDTOs(Beacon beacon) {
    return beacon
      .getUses()
      .stream()
      .map(useMapper::toDTO)
      .collect(Collectors.toList());
  }

  private BeaconPersonDTO getOwnerDTO(Beacon beacon) {
    if (beacon.getOwner() != null) {
      return personMapper.toDTO(beacon.getOwner());
    }
    return null;
  }

  private List<BeaconPersonDTO> getEmergencyContactDTOs(Beacon beacon) {
    return beacon
      .getEmergencyContacts()
      .stream()
      .map(personMapper::toDTO)
      .collect(Collectors.toList());
  }

  private BeaconDTO buildBeaconDTO(
    Beacon beacon,
    List<BeaconUseDTO> useDTOs,
    BeaconPersonDTO ownerDTO,
    List<BeaconPersonDTO> emergencyContactDTOs
  ) {
    final var beaconDTO = beaconMapper.toDTO(beacon);
    final var useRelationshipDTO = useRelationshipMapper.toDTO(
      useDTOs.stream().map(dto -> (DomainDTO) dto).collect(Collectors.toList())
    );
    final var ownerRelationshipDTO = personRelationshipMapper.toDTO(ownerDTO);
    final var emergencyContactRelationshipDTO = personRelationshipMapper.toDTO(
      emergencyContactDTOs
        .stream()
        .map(dto -> (DomainDTO) dto)
        .collect(Collectors.toList())
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
