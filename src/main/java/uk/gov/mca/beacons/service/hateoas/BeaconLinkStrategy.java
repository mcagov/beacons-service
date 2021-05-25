package uk.gov.mca.beacons.service.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static uk.gov.mca.beacons.service.hateoas.BeaconRolesService.SupportedPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.beacons.BeaconsController;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.Beacon;

@Service
public class BeaconLinkStrategy implements IHateoasLinkStrategy<Beacon> {

  private final Class<BeaconsController> beaconController =
    BeaconsController.class;

  private final BeaconRolesService beaconRolesService;

  @Autowired
  public BeaconLinkStrategy(BeaconRolesService beaconsRolesService) {
    this.beaconRolesService = beaconsRolesService;
  }

  public boolean userCanGetEntity(Beacon domain) {
    return true;
  }

  public String getGetPath(Beacon domain) {
    final var methodRoute = WebMvcLinkBuilder
      .methodOn(beaconController)
      .findByUuid(domain.getId());
    return HateoasLinkPathBuilder.build(linkTo(methodRoute));
  }

  public boolean userCanPatchEntity(Beacon domain) {
    return this.beaconRolesService.getUserRoles()
      .contains(SupportedPermissions.APPROLE_UPDATE_RECORDS);
  }

  public String getPatchPath(Beacon domain) {
    final var methodRoute = WebMvcLinkBuilder
      .methodOn(beaconController)
      .update(domain.getId(), new WrapperDTO<BeaconDTO>());
    return HateoasLinkPathBuilder.build(linkTo(methodRoute));
  }
}
