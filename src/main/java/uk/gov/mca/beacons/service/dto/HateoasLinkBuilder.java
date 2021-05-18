package uk.gov.mca.beacons.service.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.beacons.BeaconsController;
import uk.gov.mca.beacons.service.model.Beacon;

@Service
public class HateoasLinkBuilder {

  public String buildGetFor(Beacon domain) {
    var controller = BeaconsController.class;
    var methodRoute = WebMvcLinkBuilder
      .methodOn(controller)
      .findByUuid(domain.getId());
    return build(linkTo(methodRoute));
  }

  public String buildPatchFor(Beacon domain) {
    var controller = BeaconsController.class;
    var methodRoute = WebMvcLinkBuilder
      .methodOn(controller)
      .update(domain.getId(), new WrapperDTO<BeaconDTO>());
    return build(linkTo(methodRoute));
  }

  // public String buildFor(BeaconUse domain) {
  // var controller = BeaconUsesController.class;
  // var methodRoute = WebMvcLinkBuilder.methodOn(controller).findByUuid(domain.getId());
  // return build(linkTo(methodRoute));
  // }

  private String build(WebMvcLinkBuilder linkBuilder) {
    return linkBuilder.toUriComponentsBuilder().build().getPath();
  }
}
