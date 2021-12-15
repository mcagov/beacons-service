package uk.gov.mca.beacons.api.registration.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.registration.application.CreateRegistrationService;
import uk.gov.mca.beacons.api.registration.domain.Registration;
import uk.gov.mca.beacons.api.registration.mappers.RegistrationMapper;

@RestController
@RequestMapping("/spring-api/registrationsv2")
@Tag(name = "Registration Controller")
public class RegistrationController {

  private final CreateRegistrationService createRegistrationService;
  private final RegistrationMapper registrationMapper;

  @Autowired
  public RegistrationController(
    CreateRegistrationService createRegistrationService,
    RegistrationMapper registrationMapper
  ) {
    this.createRegistrationService = createRegistrationService;
    this.registrationMapper = registrationMapper;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<RegistrationDTO> register(
    @Valid @RequestBody RegistrationDTO registrationDTO
  ) {
    Registration registration = registrationMapper.fromDTO(registrationDTO);
    Registration savedRegistration = createRegistrationService.register(
      registration
    );
    return new ResponseEntity<>(
      registrationMapper.toDTO(savedRegistration),
      HttpStatus.CREATED
    );
  }
}
