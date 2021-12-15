package uk.gov.mca.beacons.api.beaconowner.application;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwner;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwnerRepository;

@Transactional
@Service("BeaconOwnerServiceV2")
public class BeaconOwnerService {

  private final BeaconOwnerRepository beaconOwnerRepository;

  @Autowired
  public BeaconOwnerService(BeaconOwnerRepository beaconOwnerRepository) {
    this.beaconOwnerRepository = beaconOwnerRepository;
  }

  public BeaconOwner create(BeaconOwner beaconOwner) {
    return beaconOwnerRepository.save(beaconOwner);
  }
}
