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

  private final Class<BeaconsController> beaconController =
    BeaconsController.class;

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
    final var methodRoute = WebMvcLinkBuilder
      .methodOn(beaconController)
      .findByUuid(domain.getId());
    return build(linkTo(methodRoute));
  }

  private String buildForPatch(Beacon domain) {
    final var methodRoute = WebMvcLinkBuilder
      .methodOn(beaconController)
      .update(domain.getId(), new WrapperDTO<BeaconDTO>());
    return build(linkTo(methodRoute));
  }

  private String build(WebMvcLinkBuilder linkBuilder) {
    return linkBuilder.toUriComponentsBuilder().build().getPath();
  }
}
