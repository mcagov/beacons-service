package uk.gov.mca.beacons.service.registrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.model.Registration;

@RestController
public class RegistrationsController {

  private final RegistrationsService registrationsService;

  @Autowired
  RegistrationsController(RegistrationsService registrationsService) {
    this.registrationsService = registrationsService;
  }

  @PostMapping("/register")
  public ResponseEntity<Registration> register(
    @RequestBody Registration registration
  ) {
    return registrationsService.register(registration);
  }
}
