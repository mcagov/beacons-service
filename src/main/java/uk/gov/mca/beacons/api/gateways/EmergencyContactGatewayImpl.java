package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.CreateEmergencyContactRequestMapper;

@Repository
@Transactional
public class EmergencyContactGatewayImpl implements EmergencyContactGateway {

  private final BeaconPersonJpaRepository beaconPersonJpaRepository;

  @Autowired
  public EmergencyContactGatewayImpl(
    BeaconPersonJpaRepository beaconPersonJpaRepository
  ) {
    this.beaconPersonJpaRepository = beaconPersonJpaRepository;
  }

  @Override
  public Person create(CreateEmergencyContactRequest request) {
    final Person emergencyContact = CreateEmergencyContactRequestMapper.toBeaconPerson(
      request
    );
    beaconPersonJpaRepository.save(emergencyContact);

    return null; //TODO: needs a PERSON domain object?
  }

  @Override
  public List<Person> findAllByBeaconId(UUID beaconId) {
    return beaconPersonJpaRepository.findEmergencyContactsByBeaconId(beaconId);
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
