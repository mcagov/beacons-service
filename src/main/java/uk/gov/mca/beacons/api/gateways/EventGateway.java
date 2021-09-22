package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.domain.events.Event;

public interface EventGateway {
    void save(Event event);
}
