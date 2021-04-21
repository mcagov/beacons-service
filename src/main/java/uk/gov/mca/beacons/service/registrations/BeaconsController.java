package uk.gov.mca.beacons.service.registrations;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;

@RestController
@RequestMapping("/beacons")
@Tag(name = "Beacons Controller")
public class BeaconsController {

  private final GetAllBeaconsService getAllBeaconsService;

  @Autowired
  public BeaconsController(GetAllBeaconsService getAllBeaconsService) {
    this.getAllBeaconsService = getAllBeaconsService;
  }

  @GetMapping(value = "/")
  public BeaconsSearchResult findAll() {
    final var results = new BeaconsSearchResult();
    results.setBeacons(getAllBeaconsService.findAll());
    return results;
  }

  @GetMapping(value = "/{hexId}")
  public Beacon findByHexId(@PathVariable("hexId") String hexId) {
    return new Beacon();
  }
}
