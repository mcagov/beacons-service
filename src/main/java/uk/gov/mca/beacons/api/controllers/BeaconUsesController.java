package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.dto.BeaconUseDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.exceptions.InvalidPatchException;
import uk.gov.mca.beacons.api.mappers.BeaconUseMapper;
import uk.gov.mca.beacons.api.services.UpdateBeaconUseService;

@RestController
@RequestMapping("/beacon-uses")
@Tag(name = "Beacon Uses Controller")
public class BeaconUsesController {

  private final BeaconUseMapper beaconUseMapper;

  private final UpdateBeaconUseService updateBeaconUseService;

  @Autowired
  public BeaconUsesController(
    BeaconUseMapper beaconUseMapper,
    UpdateBeaconUseService updateBeaconUseService
  ) {
    this.beaconUseMapper = beaconUseMapper;
    this.updateBeaconUseService = updateBeaconUseService;
  }

  @PatchMapping(value = "/{uuid}")
  @PreAuthorize("hasAuthority('APPROLE_UPDATE_RECORDS')")
  public ResponseEntity<Void> update(
    @PathVariable("uuid") UUID uuid,
    @RequestBody WrapperDTO<BeaconUseDTO> beaconUseDto
  ) {
    if (
      !idIsEqualToDtoId(uuid, beaconUseDto)
    ) throw new InvalidPatchException();

    final var beaconToUpdate = beaconUseMapper.fromDTO(beaconUseDto.getData());
    updateBeaconUseService.update(uuid, beaconToUpdate);

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  private boolean idIsEqualToDtoId(
    UUID id,
    WrapperDTO<BeaconUseDTO> beaconUseDto
  ) {
    return id.equals(beaconUseDto.getData().getId());
  }
}
