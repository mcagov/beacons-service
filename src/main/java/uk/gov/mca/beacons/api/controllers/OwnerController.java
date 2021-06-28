package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.api.services.CreateOwnerService;

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
            @RequestBody WrapperDTO<BeaconPersonDTO> dto
    ) {
        final Person person = beaconPersonMapper.fromDTO(dto.getData());

        return beaconPersonMapper.toWrapperDTO(
                createOwnerService.execute(person)
        );
    }
}
