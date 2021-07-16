package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/migrate")
@Tag(
  name = "Migration Controller for existing beacon records, owners, emergency contacts, and uses"
)
@Profile("migration")
public class MigrationController {

  @GetMapping(value = "/")
  public String base() {
    return "Migration Controller";
  }
}
