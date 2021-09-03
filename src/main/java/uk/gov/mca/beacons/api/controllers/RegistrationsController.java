package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.documentation.RegisterBeaconDocumentation;
import uk.gov.mca.beacons.api.dto.UpdateRegistrationRequest;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.services.CreateRegistrationService;
import uk.gov.mca.beacons.api.services.UpdateRegistrationService;

@RestController
@RequestMapping("/registrations")
@Tag(name = "Registrations Controller")
public class RegistrationsController {

  private final CreateRegistrationService createRegistrationService;
  private final UpdateRegistrationService updateRegistrationService;

  @Autowired
  public RegistrationsController(
    CreateRegistrationService createRegistrationService,
    UpdateRegistrationService updateRegistrationService
  ) {
    this.createRegistrationService = createRegistrationService;
    this.updateRegistrationService = updateRegistrationService;
  }

  @PostMapping(
    value = "/register",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @RegisterBeaconDocumentation
  public ResponseEntity<Beacon> register(@Valid @RequestBody Beacon beacon) {
    return new ResponseEntity<>(
      createRegistrationService.register(beacon),
      HttpStatus.CREATED
    );
  }

  @PatchMapping(value = "/register/{uuid}")
  public ResponseEntity<Beacon> update(
    @Valid @RequestBody Beacon beacon,
    @PathVariable("uuid") UUID beaconId
  ) {
    final var updateRequest = new UpdateRegistrationRequest(beaconId, beacon);
    return ResponseEntity.ok(updateRegistrationService.update(updateRequest));
  }
}
