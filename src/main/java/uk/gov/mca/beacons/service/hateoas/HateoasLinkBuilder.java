package uk.gov.mca.beacons.service.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

  private final BeaconRolesService beaconRolesService;

  @Autowired
  public HateoasLinkBuilder(BeaconRolesService beaconsRolesService) {
    this.beaconRolesService = beaconsRolesService;
  }

  public List<HateoasLink> getLinksFor(Beacon domain) {
    ArrayList<HateoasLink> links = new ArrayList<HateoasLink>();
    addGet(domain, links);
    addPatch(domain, links);
    return links;
  }

  private void addGet(Beacon domain, ArrayList<HateoasLink> links) {
    final var methodRoute = WebMvcLinkBuilder
      .methodOn(beaconController)
      .findByUuid(domain.getId());
    final var link = build(linkTo(methodRoute));
    links.add(new HateoasLink(SupportedMethod.GET.toString(), link));
  }

  private void addPatch(Beacon domain, ArrayList<HateoasLink> links) {
    if (
      !this.beaconRolesService.getUserRoles().contains("APPROLE_UPDATE_RECORDS")
    ) return;
    final var methodRoute = WebMvcLinkBuilder
      .methodOn(beaconController)
      .update(domain.getId(), new WrapperDTO<BeaconDTO>());
    final var link = build(linkTo(methodRoute));
    links.add(new HateoasLink(SupportedMethod.PATCH.toString(), link));
  }

  private String build(WebMvcLinkBuilder linkBuilder) {
    return linkBuilder.toUriComponentsBuilder().build().getPath();
  }
}
