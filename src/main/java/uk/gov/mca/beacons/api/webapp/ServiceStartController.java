package uk.gov.mca.beacons.api.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceStartController {

  @GetMapping("/")
  public String serviceStart() {
    return "service-start";
  }
}
