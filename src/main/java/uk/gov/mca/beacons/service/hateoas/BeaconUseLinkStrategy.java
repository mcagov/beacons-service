package uk.gov.mca.beacons.service.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.uses.BeaconUsesController;

@Service
public class BeaconUseLinkStrategy implements IHateoasLinkStrategy<BeaconUse> {

  private final Class<BeaconUsesController> beaconUsesController =
    BeaconUsesController.class;

  private final BeaconRolesService beaconRolesService;

  @Autowired
  public BeaconUseLinkStrategy(BeaconRolesService beaconsRolesService) {
    this.beaconRolesService = beaconsRolesService;
  }

  public boolean checkGetPermission(BeaconUse domain) {
    return false;
  }

  public String getGetPath(BeaconUse domain) {
    throw new NotImplementedException();
  }

  public boolean checkPatchPermission(BeaconUse domain) {
    return this.beaconRolesService.getUserRoles()
      .contains("APPROLE_UPDATE_RECORDS");
  }

  public String getPatchPath(BeaconUse domain) {
    final var methodRoute = WebMvcLinkBuilder
      .methodOn(beaconUsesController)
      .update(domain.getId(), new WrapperDTO<BeaconUseDTO>());
    return HateoasLinkManager.build(linkTo(methodRoute));
  }
}
