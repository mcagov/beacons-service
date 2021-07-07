package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;
import uk.gov.mca.beacons.api.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.CreateOwnerRequestMapper;

@Repository
@Transactional
public class OwnerGatewayImpl implements OwnerGateway {

  private final BeaconPersonJpaRepository beaconPersonRepository;

  @Autowired
  public OwnerGatewayImpl(BeaconPersonJpaRepository beaconPersonRepository) {
    this.beaconPersonRepository = beaconPersonRepository;
  }

  @Override
  public Person create(CreateOwnerRequest request) {
    final Person owner = CreateOwnerRequestMapper.toBeaconPerson(request);
    return beaconPersonRepository.save(owner);
  }

  @Override
  public Person findByBeaconId(UUID beaconId) {
    return beaconPersonRepository.findOwnerByBeaconId(beaconId);
  }

  @Override
  public Person read(Person request) {
    throw new RuntimeException();
  }

  @Override
  public Person update(Person request) {
    throw new RuntimeException();
  }

  @Override
  public Person delete(Person request) {
    throw new RuntimeException();
  }
}
