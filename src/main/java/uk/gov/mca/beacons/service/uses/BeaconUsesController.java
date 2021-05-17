package uk.gov.mca.beacons.service.uses;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/uses")
@Tag(name = "Beacon Uses Controller")
public class BeaconUsesController {

  private final BeaconUseMapper beaconUseMapper;

  private final BeaconUsesService beaconUsesService;

  @Autowired
  public BeaconUsesController(
    BeaconUseMapper beaconUseMapper,
    BeaconUsesService beaconUsesService
  ) {
    this.beaconUseMapper = beaconUseMapper;
    this.beaconUsesService = beaconUsesService;
  }

  @PatchMapping(value = "/{uuid}")
  public void update(
    @PathVariable("uuid") UUID uuid,
    @RequestBody WrapperDTO<BeaconUseDTO> beaconUseDto
  ) {
    if (!idEqualsDtoId(uuid, beaconUseDto)) throw new InvalidPatchException();

    final var beaconToUpdate = beaconUseMapper.fromDTO(beaconUseDto);
    beaconUsesService.update(uuid, beaconToUpdate);
  }

  private boolean idEqualsDtoId(
    UUID id,
    WrapperDTO<BeaconUseDTO> beaconUseDto
  ) {
    return id.equals(beaconUseDto.getData().getId());
  }
}
