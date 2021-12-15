package uk.gov.mca.beacons.api.beacon.application;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.beacon.domain.BeaconRepository;

@Transactional
@Service("BeaconServiceV2")
public class BeaconService {

  private final BeaconRepository beaconRepository;

  @Autowired
  BeaconService(BeaconRepository beaconRepository) {
    this.beaconRepository = beaconRepository;
  }

  public Beacon create(Beacon beacon) {
    return beaconRepository.save(beacon);
  }
}
