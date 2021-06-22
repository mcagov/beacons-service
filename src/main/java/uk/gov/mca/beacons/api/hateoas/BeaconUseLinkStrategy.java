package uk.gov.mca.beacons.api.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static uk.gov.mca.beacons.api.hateoas.BeaconRolesService.SupportedPermissions;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.db.BeaconUse;
import uk.gov.mca.beacons.api.dto.BeaconUseDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.controllers.BeaconUsesController;

@Service
public class BeaconUseLinkStrategy implements IHateoasLinkStrategy<BeaconUse> {

    private final Class<BeaconUsesController> beaconUsesController =
            BeaconUsesController.class;

    private final BeaconRolesService beaconRolesService;

    @Autowired
    public BeaconUseLinkStrategy(BeaconRolesService beaconsRolesService) {
        this.beaconRolesService = beaconsRolesService;
    }

    public boolean userCanGetEntity(BeaconUse domain) {
        return false;
    }

    public String getGetPath(BeaconUse domain) {
        throw new NotImplementedException();
    }

    public boolean userCanPatchEntity(BeaconUse domain) {
        return this.beaconRolesService.getUserRoles()
                .contains(SupportedPermissions.APPROLE_UPDATE_RECORDS);
    }

    public String getPatchPath(BeaconUse domain) {
        final var methodRoute = WebMvcLinkBuilder
                .methodOn(beaconUsesController)
                .update(domain.getId(), new WrapperDTO<BeaconUseDTO>());
        return HateoasLinkPathBuilder.build(linkTo(methodRoute));
    }
}
