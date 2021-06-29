package uk.gov.mca.beacons.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.gateways.PersonGateway;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.CreateOwnerRequestMapper;

@Service
@Transactional
public class CreateOwnerService {

    private final PersonGateway personGateway;

    @Autowired
    public CreateOwnerService(PersonGateway personGateway) {
        this.personGateway = personGateway;
    }

    public Person execute(Person person) {
        final var createOwnerRequest = CreateOwnerRequestMapper.fromBeaconPerson(
                person
        );
        return personGateway.save(createOwnerRequest);
    }
}
