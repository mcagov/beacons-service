package uk.gov.mca.beacons.service.registrations;

import java.util.List;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@Service
@Transactional
public class GetAllBeaconsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(GetAllBeaconsService.class);

  private final BeaconRepository beaconRepository;
  private final BeaconUseRepository beaconUseRepository;
  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public GetAllBeaconsService(BeaconRepository beaconRepository, BeaconUseRepository beaconUseRepository,
      BeaconPersonRepository beaconPersonRepository) {
    this.beaconRepository = beaconRepository;
    this.beaconPersonRepository = beaconPersonRepository;
    this.beaconUseRepository = beaconUseRepository;
  }

  public List<Beacon> findAll()  {
    final var result = beaconRepository.findAll();
    final var allBeacons = new ArrayList<Beacon>();
    result.forEach(allBeacons::add);

    return allBeacons;
  }
}