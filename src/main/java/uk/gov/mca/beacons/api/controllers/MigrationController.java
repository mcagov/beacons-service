package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.LegacyBeaconDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;
import uk.gov.mca.beacons.api.services.LegacyBeaconService;

@RestController
@RequestMapping("/spring-api/migrate")
@Tag(name = "Migration Controller")
@Profile("migration")
public class MigrationController {

  private final LegacyBeaconService legacyBeaconService;
  private final LegacyBeaconMapper legacyBeaconMapper;

  @Autowired
  public MigrationController(
    LegacyBeaconService legacyBeaconService,
    LegacyBeaconMapper legacyBeaconMapper
  ) {
    this.legacyBeaconService = legacyBeaconService;
    this.legacyBeaconMapper = legacyBeaconMapper;
  }

  @GetMapping(value = "/")
  public String base() {
    return "Migration Controller";
  }

  @GetMapping(value = "/delete-all-legacy-beacons")
  public ResponseEntity<Void> deleteAllLegacyBeacons() {
    legacyBeaconService.deleteAll();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping(value = "/legacy-beacon")
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<LegacyBeaconDTO> createLegacyBeacon(
    @RequestBody WrapperDTO<LegacyBeaconDTO> wrapperDTO
  ) {
    final LegacyBeacon beacon = legacyBeaconMapper.fromDTO(
      wrapperDTO.getData()
    );

    return legacyBeaconMapper.toWrapperDTO(legacyBeaconService.create(beacon));
  }
}
