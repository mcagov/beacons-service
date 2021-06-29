package uk.gov.mca.beacons.api.services;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.jpa.entities.Person;

@Service
@Transactional
public class GetPersonByIdService {
    public Person execute(UUID id, PersonType personType) {
        return null;
    }
}
