package uk.gov.mca.beacons.api.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.dto.BeaconDTO;
import uk.gov.mca.beacons.api.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.api.dto.BeaconUseDTO;
import uk.gov.mca.beacons.api.dto.DomainDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

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

  public WrapperDTO<List<BeaconDTO>> buildDTO(List<Beacon> beacons) {
    final var wrapper = new WrapperDTO<List<BeaconDTO>>();
    final List<BeaconDTO> beaconDTOs = new ArrayList<>();

    beacons.forEach(beacon -> {
      final var beaconDTO = getBeaconDTO(beacon);
      final var useDTOs = getUseDTOs(beacon);
      final var ownerDTO = getOwnerDTO(beacon);
      final var emergencyContactDTOs = getEmergencyContactDTOs(beacon);

      useDTOs.forEach(wrapper::addIncluded);
      wrapper.addIncluded(ownerDTO);
      emergencyContactDTOs.forEach(wrapper::addIncluded);
      beaconDTOs.add(beaconDTO);
    });

    wrapper.setData(beaconDTOs);
    wrapper.addMeta("count", beaconDTOs.size());

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

  @SuppressWarnings("rawtypes")
  private BeaconDTO buildBeaconDTO(
    Beacon beacon,
    List<BeaconUseDTO> useDTOs,
    BeaconPersonDTO ownerDTO,
    List<BeaconPersonDTO> emergencyContactDTOs
  ) {
    final var beaconDTO = beaconMapper.toDTO(beacon);
    if (useDTOs.size() > 0) {
      final var useRelationshipDTO = useRelationshipMapper.toDTO(
        useDTOs
          .stream()
          .map(dto -> (DomainDTO) dto)
          .collect(Collectors.toList())
      );
      beaconDTO.addRelationship("uses", useRelationshipDTO);
    }
    if (ownerDTO != null) {
      final var ownerRelationshipDTO = personRelationshipMapper.toDTO(ownerDTO);
      beaconDTO.addRelationship("owner", ownerRelationshipDTO);
    }
    if (emergencyContactDTOs.size() > 0) {
      final var emergencyContactRelationshipDTO = personRelationshipMapper.toDTO(
        emergencyContactDTOs
          .stream()
          .map(dto -> (DomainDTO) dto)
          .collect(Collectors.toList())
      );
      beaconDTO.addRelationship(
        "emergencyContacts",
        emergencyContactRelationshipDTO
      );
    }

    return beaconDTO;
  }
}
