package uk.gov.mca.beacons.service.person;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.AccountHolderIdDTO;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.service.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@RestController
@RequestMapping("/person")
@Tag(name = "Beacon Person")
public class BeaconPersonController {

  private final BeaconPersonMapper beaconPersonMapper;
  private final CreateBeaconPersonService createBeaconPersonService;

  @Autowired
  public BeaconPersonController(
    BeaconPersonMapper beaconPersonMapper,
    CreateBeaconPersonService createBeaconPersonService
  ) {
    this.beaconPersonMapper = beaconPersonMapper;
    this.createBeaconPersonService = createBeaconPersonService;
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
      createBeaconPersonService.execute(newBeaconPerson)
    );
  }
}
