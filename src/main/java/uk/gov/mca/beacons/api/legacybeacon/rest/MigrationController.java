package uk.gov.mca.beacons.api.legacybeacon.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-api/migratev2")
@Tag(name = "Migration Controller")
@Profile("migration")
public class MigrationController {}
