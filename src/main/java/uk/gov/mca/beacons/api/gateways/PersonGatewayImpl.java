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
public class PersonGatewayImpl implements PersonGateway {

  private final BeaconPersonJpaRepository beaconPersonRepository;

  @Autowired
  public PersonGatewayImpl(BeaconPersonJpaRepository beaconPersonRepository) {
    this.beaconPersonRepository = beaconPersonRepository;
  }

  @Override
  public Person save(CreateOwnerRequest request) {
    final Person owner = CreateOwnerRequestMapper.toBeaconPerson(request);
    return beaconPersonRepository.save(owner);
  }

  @Override
  public Person getById(UUID id) {
    return this.beaconPersonRepository.getById(id);
  }

  @Override
  public Person findByBeaconId(UUID beaconId) {
    return beaconPersonRepository.findOwnerByBeaconId(beaconId);
  }
}
