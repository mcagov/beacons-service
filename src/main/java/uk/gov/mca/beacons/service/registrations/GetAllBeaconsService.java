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
  public GetAllBeaconsService(BeaconRepository beaconRepository, BeaconPersonRepository beaconPersonRepository,
      BeaconUseRepository beaconUseRepository, BeaconsRelationshipMapper beaconsRelationshipMapper) {
    this.beaconRepository = beaconRepository;
    this.beaconPersonRepository = beaconPersonRepository;
    this.beaconUseRepository = beaconUseRepository;
    this.beaconsRelationshipMapper = beaconsRelationshipMapper;
  }

  public List<Beacon> findAll() {
    final List<Beacon> allBeacons = beaconRepository.findAll();
    final List<BeaconPerson> allBeaconPersons = beaconPersonRepository.findAll();
    final List<BeaconUse> allBeaconUses = beaconUseRepository.findAll();
    if (allBeacons.size() == 0)
      return emptyList();

    return beaconsRelationshipMapper.getMappedBeacons(allBeacons, allBeaconPersons, allBeaconUses);
  }

  public WrapperDTO<BeaconDTO> find(UUID id) {
    final Optional<Beacon> beacon = beaconRepository.findById(id);
    if (beacon.isEmpty())
      return null;

    final List<BeaconPerson> beaconPersons = beaconPersonRepository.findAllByBeaconId(id);
    final List<BeaconUse> beaconUses = beaconUseRepository.findAllByBeaconId(id);

    var mappedBeacon = beaconsRelationshipMapper.getMappedBeacons(List.of(beacon.get()), beaconPersons, beaconUses)
        .get(0);

    return convertToDTO(mappedBeacon);
  }

  private WrapperDTO<BeaconDTO> convertToDTO(Beacon beacon) {
    var beaconDTO = BeaconDTO.from(beacon);

    List<DomainDTO> useDTOs = beacon.getUses().stream().map(u -> BeaconUseDTO.from(u)).collect(Collectors.toList());
    var useRelationshipDTO = RelationshipDTO.from(useDTOs) ;
    
    beaconDTO.AddRelationship("uses", useRelationshipDTO);

    var wrapper = new WrapperDTO<BeaconDTO>();
    wrapper.AddData(beaconDTO);
    useDTOs.forEach(wrapper::AddIncluded);

    return wrapper;
  }

  

}