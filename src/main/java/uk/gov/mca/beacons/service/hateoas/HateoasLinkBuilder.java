package uk.gov.mca.beacons.service.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.beacons.BeaconsController;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
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
    final var userRoles = SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getAuthorities()
      .stream();
    if (method == SupportedMethod.GET) dto.addLink(
      new HateoasLink(method.toString(), buildForGet(domain))
    ); else if (
      method == SupportedMethod.PATCH &&
      userRoles.anyMatch(
        role -> role.getAuthority().equals("APPROLE_UPDATE_RECORDS")
      )
    ) dto.addLink(new HateoasLink(method.toString(), buildForPatch(domain)));
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
