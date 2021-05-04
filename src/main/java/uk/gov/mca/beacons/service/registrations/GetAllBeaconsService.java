package uk.gov.mca.beacons.service.registrations;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.DomainDTO;
import uk.gov.mca.beacons.service.dto.RelationshipDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Service
@Transactional
public class GetAllBeaconsService {

  private final BeaconRepository beaconRepository;
  private final BeaconPersonRepository beaconPersonRepository;
  private final BeaconUseRepository beaconUseRepository;
  private final BeaconsRelationshipMapper beaconsRelationshipMapper;

  @Autowired
  public GetAllBeaconsService(
    BeaconRepository beaconRepository,
    BeaconPersonRepository beaconPersonRepository,
    BeaconUseRepository beaconUseRepository,
    BeaconsRelationshipMapper beaconsRelationshipMapper
  ) {
    this.beaconRepository = beaconRepository;
    this.beaconPersonRepository = beaconPersonRepository;
    this.beaconUseRepository = beaconUseRepository;
    this.beaconsRelationshipMapper = beaconsRelationshipMapper;
  }

  public List<Beacon> findAll() {
    final List<Beacon> allBeacons = beaconRepository.findAll();
    final List<BeaconPerson> allBeaconPersons = beaconPersonRepository.findAll();
    final List<BeaconUse> allBeaconUses = beaconUseRepository.findAll();
    if (allBeacons.size() == 0) return emptyList();

    return beaconsRelationshipMapper.getMappedBeacons(
      allBeacons,
      allBeaconPersons,
      allBeaconUses
    );
  }

  public WrapperDTO<BeaconDTO> find(UUID id) {
    final Optional<Beacon> beacon = beaconRepository.findById(id);
    final List<BeaconPerson> beaconPersons = beaconPersonRepository.findAllByBeaconId(
      id
    );
    final List<BeaconUse> beaconUses = beaconUseRepository.findAllByBeaconId(
      id
    );

    if (beacon.isEmpty()) return null;

    var mappedBeacon = beaconsRelationshipMapper
      .getMappedBeacons(List.of(beacon.get()), beaconPersons, beaconUses)
      .get(0);

    return convertToDTO(mappedBeacon);
  }

  private WrapperDTO<BeaconDTO> convertToDTO(Beacon beacon) {
    var dto = new BeaconDTO();
    dto.setId(beacon.getId());
    dto.AddAttribute("hexId", beacon.getHexId());
    dto.AddAttribute("status", beacon.getBeaconStatus());
    dto.AddAttribute("manufacturer", beacon.getManufacturer());
    dto.AddAttribute("createdDate", beacon.getCreatedDate());
    dto.AddAttribute("model", beacon.getModel());
    dto.AddAttribute(
      "manufacturerSerialNumber",
      beacon.getManufacturerSerialNumber()
    );
    dto.AddAttribute("chkCode", beacon.getChkCode());
    dto.AddAttribute("batteryExpiryDate", beacon.getBatteryExpiryDate());
    dto.AddAttribute("lastServicedDate", beacon.getLastServicedDate());

    var wrapper = new WrapperDTO<BeaconDTO>();
    wrapper.AddData(dto);

    AddRelatedUsesToDTO(beacon, dto, wrapper);

    return wrapper;
  }

  private void AddRelatedUsesToDTO(
    Beacon beacon,
    BeaconDTO dto,
    WrapperDTO<BeaconDTO> wrapper
  ) {
    List<DomainDTO> useDtos = beacon
      .getUses()
      .stream()
      .map(u -> convertToBeaconUseDTO(u))
      .collect(Collectors.toList());

    var rel = new RelationshipDTO();
    rel.AddLink("self", "TBD");
    rel.AddLink("related", "TDB");
    useDtos.forEach(rel::AddData);

    dto.AddRelationship("uses", rel);

    useDtos.forEach(wrapper::AddIncluded);
  }

  private BeaconUseDTO convertToBeaconUseDTO(BeaconUse use) {
    var dto = new BeaconUseDTO();
    dto.setId(use.getId());
    dto.AddAttribute("environment", use.getEnvironment());
    dto.AddAttribute("activity", use.getActivity());
    dto.AddAttribute("moreDetails", use.getMoreDetails());
    return dto;
  }
}
