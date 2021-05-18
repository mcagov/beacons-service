package uk.gov.mca.beacons.service.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.beacons.BeaconsController;
import uk.gov.mca.beacons.service.model.Beacon;

@Service
public class HateoasLinkBuilder {

  public enum SupportedMethod {
    GET,
    PATCH,
  }

  public void addLinkFor(Beacon domain, SupportedMethod method, BeaconDTO dto) {
    if (method == SupportedMethod.GET) dto.addLink(
      method.toString(),
      buildForGet(domain)
    ); else if (method == SupportedMethod.PATCH) dto.addLink(
      method.toString(),
      buildForPatch(domain)
    );
  }

  private String buildForGet(Beacon domain) {
    var controller = BeaconsController.class;
    var methodRoute = WebMvcLinkBuilder
      .methodOn(controller)
      .findByUuid(domain.getId());
    return build(linkTo(methodRoute));
  }

  private String buildForPatch(Beacon domain) {
    var controller = BeaconsController.class;
    var methodRoute = WebMvcLinkBuilder
      .methodOn(controller)
      .update(domain.getId(), new WrapperDTO<BeaconDTO>());
    return build(linkTo(methodRoute));
  }

  private String build(WebMvcLinkBuilder linkBuilder) {
    return linkBuilder.toUriComponentsBuilder().build().getPath();
  }
}
