package uk.gov.mca.beacons.service.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.mappers.CreateOwnerRequestMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;

@Repository
public class OwnerGatewayImpl implements OwnerGateway {

  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public OwnerGatewayImpl(BeaconPersonRepository beaconPersonRepository) {
    this.beaconPersonRepository = beaconPersonRepository;
  }

  public void save(CreateOwnerRequest request) {
    final BeaconPerson owner = CreateOwnerRequestMapper.toBeaconPerson(request);
    beaconPersonRepository.save(owner);
  }
}
