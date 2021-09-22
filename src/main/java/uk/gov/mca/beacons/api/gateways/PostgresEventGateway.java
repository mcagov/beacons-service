package uk.gov.mca.beacons.api.gateways;

import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.domain.events.Event;

@Repository
public class PostgresEventGateway implements EventGateway {
    @Override
    public void save(Event event) {
        // SQL queries rather than JPA ORM
    }
}
