package uk.gov.mca.beacons.service.beacons;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;

@RestController
@RequestMapping("/beacons")
@Tag(name = "Beacons Controller")
public class BeaconsController {

  private final BeaconsService beaconsService;
  private final BeaconsResponseFactory responseFactory;

  @Autowired
  public BeaconsController(
    BeaconsService beaconsService,
    BeaconsResponseFactory responseFactory
  ) {
    this.beaconsService = beaconsService;
    this.responseFactory = responseFactory;
  }

  @GetMapping
  public BeaconsSearchResult findAll() {
    final var results = new BeaconsSearchResult();
    results.setBeacons(beaconsService.findAll());
    return results;
  }

  @GetMapping(value = "/{uuid}")
  public WrapperDTO<BeaconDTO> findByUuid(@PathVariable("uuid") UUID uuid) {
    final var beacon = beaconsService.find(uuid);
    return responseFactory.buildDTO(beacon);
  }

}
