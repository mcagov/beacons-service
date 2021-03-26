package uk.gov.mca.beacons.service.registrations;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.mca.beacons.service.model.Registration;

@Service
public class RegistrationsService {

  public Registration register(Registration registration) {
    // TODO: Persist beacon, owner etc. to database, send confirmation email

    // Create new BeaconPerson to represent owner, persist
    // For each beacon posted, create new Beacon and persist
    // -----> For each use associated with Beacon, create new BeaconUse and persist
    // For each beacon posted, create new Beacon and persist

    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(registration.getId())
      .toUri();

    return registration;
  }
}