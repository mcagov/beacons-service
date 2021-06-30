package uk.gov.mca.beacons.api.services;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.gateways.PersonGateway;
import uk.gov.mca.beacons.api.jpa.entities.Person;

@Service
@Transactional
public class GetPersonByIdService {

  private final PersonGateway personGateway;

  public GetPersonByIdService(PersonGateway personGateway) {
    this.personGateway = personGateway;
  }

  public Person execute(UUID id, PersonType personType) {
    Person foundPerson = personGateway.getById(id);

    if (foundPerson.getPersonType() != personType) return null;

    return foundPerson;
  }

  public Person execute(UUID id) {
    return personGateway.getById(id);
  }
}
