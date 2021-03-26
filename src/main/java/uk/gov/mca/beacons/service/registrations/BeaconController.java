package uk.gov.mca.beacons.service.registrations;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.model.Beacon;

@RestController
@RequestMapping("/beacons")
@Tag(name = "Beacons Controller")
public class BeaconController {

  @GetMapping(value = "/{hexId}")
  public Beacon findByHexId(@PathVariable("hexId") String hexId) {
    return new Beacon();
  }
}
