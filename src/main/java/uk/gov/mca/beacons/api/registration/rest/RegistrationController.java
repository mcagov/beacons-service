package uk.gov.mca.beacons.api.registration.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.api.beacon.domain.BeaconId;
import uk.gov.mca.beacons.api.registration.application.RegistrationService;
import uk.gov.mca.beacons.api.registration.domain.Registration;
import uk.gov.mca.beacons.api.registration.mappers.RegistrationMapper;

@RestController
@RequestMapping("/spring-api/registrationsv2")
@Tag(name = "Registration Controller")
public class RegistrationController {

  private final RegistrationService registrationService;
  private final RegistrationMapper registrationMapper;

  @Autowired
  public RegistrationController(
    RegistrationService createRegistrationService,
    RegistrationMapper registrationMapper
  ) {
    this.registrationService = createRegistrationService;
    this.registrationMapper = registrationMapper;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<RegistrationDTO> register(
    @Valid @RequestBody CreateRegistrationDTO createRegistrationDTO
  ) {
    Registration registration = registrationMapper.fromDTO(
      createRegistrationDTO
    );
    Registration savedRegistration = registrationService.register(registration);
    return new ResponseEntity<>(
      registrationMapper.toDTO(savedRegistration),
      HttpStatus.CREATED
    );
  }

  @PatchMapping(value = "/register/{uuid}")
  public ResponseEntity<RegistrationDTO> update(
    @Valid @RequestBody CreateRegistrationDTO dto,
    @PathVariable("uuid") UUID rawBeaconId
  ) {
    BeaconId beaconId = new BeaconId(rawBeaconId);
    Registration registration = registrationMapper.fromDTO(dto);
    Registration updatedRegistration = registrationService.update(
      beaconId,
      registration
    );
    RegistrationDTO updateDTO = registrationMapper.toDTO(updatedRegistration);
    return ResponseEntity.ok(updateDTO);
  }
}
