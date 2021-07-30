package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.api.dto.AccountHolderDTO;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.dto.LegacyBeaconDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;

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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<LegacyBeaconDTO> createLegacyBeacon(
          @RequestBody WrapperDTO<LegacyBeaconDTO> wrapperDTO
  ) {
    final LegacyBeaconDTO newLegacyBeaconRequest = wrapperDTO.getData();

    return legacyBeaconMapper.toWrapperDTO(
            legacyBeaconService.create(newLegacyBeaconRequest)
    );
  }
}
