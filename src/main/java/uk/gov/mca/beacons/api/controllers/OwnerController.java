package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.api.services.CreateOwnerService;
import uk.gov.mca.beacons.api.services.GetPersonByIdService;

@RestController
@RequestMapping("/owners")
@Tag(name = "Owner Controller")
public class OwnerController {

  private final BeaconPersonMapper beaconPersonMapper;
  private final CreateOwnerService createOwnerService;
  private final GetPersonByIdService getPersonByIdService;

  @Autowired
  public OwnerController(
    BeaconPersonMapper beaconPersonMapper,
    CreateOwnerService createOwnerService,
    GetPersonByIdService getPersonByIdService
  ) {
    this.beaconPersonMapper = beaconPersonMapper;
    this.createOwnerService = createOwnerService;
    this.getPersonByIdService = getPersonByIdService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<BeaconPersonDTO> createOwner(
    @RequestBody @Valid WrapperDTO<BeaconPersonDTO> dto
  ) {
    final Person person = beaconPersonMapper.fromDTO(dto.getData());

    return beaconPersonMapper.toWrapperDTO(createOwnerService.execute(person));
  }

  @GetMapping(value = "/{id}")
  public WrapperDTO<BeaconPersonDTO> getOwner(@PathVariable("id") UUID id) {
    final Person owner = getPersonByIdService.execute(id, PersonType.OWNER);

    if (owner == null) throw new ResourceNotFoundException();

    return beaconPersonMapper.toWrapperDTO(owner);
  }
}
