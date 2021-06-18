package uk.gov.mca.beacons.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class CreateBeaconPersonService {

  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public CreateBeaconPersonService(
    BeaconPersonRepository beaconPersonRepository
  ) {
    this.beaconPersonRepository = beaconPersonRepository;
  }

  public BeaconPerson execute(BeaconPerson beaconPerson) {
    return beaconPersonRepository.save(beaconPerson);
  }
}
