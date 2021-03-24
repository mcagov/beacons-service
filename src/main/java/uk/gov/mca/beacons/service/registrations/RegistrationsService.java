package uk.gov.mca.beacons.service.registrations;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.gov.mca.beacons.service.model.Registration;

@Service
public class RegistrationsService {

  public ResponseEntity<Registration> register(Registration registration) {
    // TODO: Persist to database, send confirmation email

    URI uri = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(registration.getId())
      .toUri();

    return ResponseEntity.created(uri).body(registration);
  }
}
