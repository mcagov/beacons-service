package uk.gov.mca.beacons.service.uses;

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
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.exceptions.InvalidPatchException;
import uk.gov.mca.beacons.service.mappers.BeaconUseMapper;

@RestController
@RequestMapping("/beacon-uses")
@Tag(name = "Beacon Uses Controller")
public class BeaconUsesController {

  private final BeaconUseMapper beaconUseMapper;

  private final BeaconUsesPatchService beaconUsesPatchService;

  @Autowired
  public BeaconUsesController(
    BeaconUseMapper beaconUseMapper,
    BeaconUsesPatchService beaconUsesPatchService
  ) {
    this.beaconUseMapper = beaconUseMapper;
    this.beaconUsesPatchService = beaconUsesPatchService;
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
    beaconUsesPatchService.update(uuid, beaconToUpdate);

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  private boolean idIsEqualToDtoId(
    UUID id,
    WrapperDTO<BeaconUseDTO> beaconUseDto
  ) {
    return id.equals(beaconUseDto.getData().getId());
  }
}
