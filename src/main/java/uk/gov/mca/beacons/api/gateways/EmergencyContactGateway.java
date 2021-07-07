package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.jpa.entities.Person;

public interface EmergencyContactGateway
  extends
    BeaconSearchGateway<Person>,
    CrudGateway<Person, CreateEmergencyContactRequest> {}
