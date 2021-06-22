package uk.gov.mca.beacons.service.owner;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@RestController
@RequestMapping("/person")
@Tag(name = "Beacon Person")
public class OwnerController {

  private final BeaconPersonMapper beaconPersonMapper;
  private final CreateOwnerService createOwnerService;

  @Autowired
  public OwnerController(
    BeaconPersonMapper beaconPersonMapper,
    CreateOwnerService createOwnerService
  ) {
    this.beaconPersonMapper = beaconPersonMapper;
    this.createOwnerService = createOwnerService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<BeaconPersonDTO> createPerson(
    @RequestBody WrapperDTO<BeaconPersonDTO> dto
  ) {
    final BeaconPerson newBeaconPerson = beaconPersonMapper.fromDTO(
      dto.getData()
    );

    return beaconPersonMapper.toWrapperDTO(
      createOwnerService.execute(newBeaconPerson)
    );
  }
}
