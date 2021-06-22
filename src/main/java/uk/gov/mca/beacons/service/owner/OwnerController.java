package uk.gov.mca.beacons.service.owner;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.dto.OwnerDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.mappers.OwnerMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@RestController
@RequestMapping("/owner")
@Tag(name = "Beacon Person")
public class OwnerController {

  private final OwnerMapper ownerMapper;
  private final CreateOwnerService createOwnerService;

  @Autowired
  public OwnerController(
    OwnerMapper ownerMapper,
    CreateOwnerService createOwnerService
  ) {
    this.ownerMapper = ownerMapper;
    this.createOwnerService = createOwnerService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<OwnerDTO> createPerson(
    @RequestBody WrapperDTO<OwnerDTO> dto
  ) {
    final BeaconPerson beaconPerson = ownerMapper.fromDTO(dto.getData());

    return ownerMapper.toWrapperDTO(createOwnerService.execute(beaconPerson));
  }
}
