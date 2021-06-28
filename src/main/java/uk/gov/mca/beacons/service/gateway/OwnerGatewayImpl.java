package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.mappers.CreateOwnerRequestMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;

@Repository
@Transactional
public class OwnerGatewayImpl implements OwnerGateway {

  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public OwnerGatewayImpl(BeaconPersonRepository beaconPersonRepository) {
    this.beaconPersonRepository = beaconPersonRepository;
  }

  @Override
  public BeaconPerson save(CreateOwnerRequest request) {
    final BeaconPerson owner = CreateOwnerRequestMapper.toBeaconPerson(request);
    return beaconPersonRepository.save(owner);
  }

  @Override
  public BeaconPerson findByBeaconId(UUID beaconId) {
    return beaconPersonRepository.findOwnerByBeaconId(beaconId);
  }
}
