package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.db.Registration;
import uk.gov.mca.beacons.api.documentation.RegisterBeaconDocumentation;
import uk.gov.mca.beacons.api.services.CreateRegistrationService;

@RestController
@RequestMapping("/registrations")
@Tag(name = "Registrations Controller")
public class RegistrationsController {

    private final CreateRegistrationService createRegistrationService;

    @Autowired
    public RegistrationsController(CreateRegistrationService createRegistrationService) {
        this.createRegistrationService = createRegistrationService;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RegisterBeaconDocumentation
    public ResponseEntity<Registration> register(
            @Valid @RequestBody Registration registration
    ) {
        return new ResponseEntity<>(
                createRegistrationService.register(registration),
                HttpStatus.CREATED
        );
    }
}
