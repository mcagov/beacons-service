package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.dto.LegacyBeaconDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;
import uk.gov.mca.beacons.api.services.LegacyBeaconService;

@RestController
@RequestMapping("/legacy-beacon")
@Tag(name = "Legacy Beacon Controller")
public class LegacyBeaconController {

  private final LegacyBeaconService legacyBeaconService;
  private final LegacyBeaconMapper legacyBeaconMapper;

  @Autowired
  public LegacyBeaconController(
    LegacyBeaconService legacyBeaconService,
    LegacyBeaconMapper legacyBeaconMapper
  ) {
    this.legacyBeaconService = legacyBeaconService;
    this.legacyBeaconMapper = legacyBeaconMapper;
  }

  @GetMapping(value = "/{uuid}")
  public WrapperDTO<LegacyBeaconDTO> findById(@PathVariable("uuid") UUID id) {
    final var optionalLegacyBeacon = legacyBeaconService
      .findById(id)
      .orElseThrow(ResourceNotFoundException::new);

    return legacyBeaconMapper.toWrapperDTO(optionalLegacyBeacon);
  }
}
