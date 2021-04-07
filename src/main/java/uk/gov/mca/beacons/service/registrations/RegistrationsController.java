package uk.gov.mca.beacons.service.registrations;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.documentation.RegisterBeaconDocumentation;
import uk.gov.mca.beacons.service.model.Registration;

@RestController
@RequestMapping("/registrations")
@Tag(name = "Registrations Controller")
public class RegistrationsController {

  private final RegistrationsService registrationsService;

  @Autowired
  public RegistrationsController(RegistrationsService registrationsService) {
    this.registrationsService = registrationsService;
  }

  @PostMapping(
    value = "/register",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @RegisterBeaconDocumentation
  public Registration register(@RequestBody Registration registration) {
    return registrationsService.register(registration);
  }
}
