package uk.gov.mca.beacons.service.owner;

import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@RestController
@RequestMapping("/owner")
@Tag(name = "Owner Controller")
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
  public WrapperDTO<BeaconPersonDTO> createOwner(
    @RequestBody @Valid WrapperDTO<BeaconPersonDTO> dto
  ) {
    final BeaconPerson beaconPerson = beaconPersonMapper.fromDTO(dto.getData());

    return beaconPersonMapper.toWrapperDTO(
      createOwnerService.execute(beaconPerson)
    );
  }
}
