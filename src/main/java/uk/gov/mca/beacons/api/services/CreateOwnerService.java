package uk.gov.mca.beacons.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.CreateOwnerRequestMapper;

@Service
@Transactional
public class CreateOwnerService {

  private final OwnerGateway ownerGateway;

  @Autowired
  public CreateOwnerService(OwnerGateway ownerGateway) {
    this.ownerGateway = ownerGateway;
  }

  public Person execute(Person person) {
    final var createOwnerRequest = CreateOwnerRequestMapper.fromBeaconPerson(
      person
    );
    return ownerGateway.save(createOwnerRequest);
  }
}
