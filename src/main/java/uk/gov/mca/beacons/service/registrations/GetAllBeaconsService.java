package uk.gov.mca.beacons.service.registrations;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.mca.beacons.service.dto.BeaconDTO;
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

    return beaconsRelationshipMapper.getMappedBeacons(allBeacons, allBeaconPersons, allBeaconUses);
  }

  public BeaconDTO find(UUID id) {
    final Optional<Beacon> beacon = beaconRepository.findById(id);
    final List<BeaconPerson> allBeaconPersons = beaconPersonRepository.findAll();
    final List<BeaconUse> allBeaconUses = beaconUseRepository.findAll();

    if(beacon.isEmpty())
    return null;

    var mappedBeacon = beaconsRelationshipMapper.getMappedBeacons(List.of(beacon.get()), allBeaconPersons, allBeaconUses).get(0);
     
    return convertToBeaconDTO(mappedBeacon);

    // return beacon.map(
    //   foundBeacon -> {
    //     List<Beacon> beaconInList = new ArrayList<Beacon>();
    //     beaconInList.add(foundBeacon);

    //     return beaconsRelationshipMapper.getMappedBeacons(beaconInList, allBeaconPersons, allBeaconUses).get(0);
    //   }
    // );
  }

  private BeaconDTO convertToBeaconDTO(Beacon beacon){
    var dto = new BeaconDTO();
    dto.setId(beacon.getId());
    dto.AddAttribute("hexId", beacon.getHexId());
    return dto;
  }

}
