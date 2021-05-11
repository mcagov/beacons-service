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
import uk.gov.mca.beacons.service.mappers.BeaconMapper;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;

@RestController
@RequestMapping("/beacons")
@Tag(name = "Beacons Controller")
public class BeaconsController {

  private final BeaconsService beaconsService;
  private final BeaconsResponseFactory responseFactory;
  private final BeaconMapper beaconMapper;

  @Autowired
  public BeaconsController(BeaconsService beaconsService, BeaconsResponseFactory responseFactory,
      BeaconMapper beaconMapper) {
    this.beaconsService = beaconsService;
    this.responseFactory = responseFactory;
    this.beaconMapper = beaconMapper;
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

  // curl -H "Content-Type: application/json" --request PATCH --data '{"data":
  // {"type": "beacons","id": "b60ec8f3-36eb-4459-aa66-a873af457595","attributes":
  // {"model": "New model value","manufacturer": "New manufacturer value"}}}' -X
  // PATCH
  // http://localhost:8080/spring-api/beacons/b60ec8f3-36eb-4459-aa66-a873af457595
  @PatchMapping(value = "/{uuid}")
  public void update(@PathVariable("uuid") UUID uuid, @RequestBody WrapperDTO<BeaconDTO> dto) {
    final var update = beaconMapper.fromDTO(dto.getData());
    if (dto.getData().getId() != uuid)
      throw new RuntimeException(); // TODO: pick a card

    beaconsService.update(uuid, update);
    return;
  }
}
