package uk.gov.mca.beacons.api.hateoas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static uk.gov.mca.beacons.api.hateoas.BeaconRolesService.SupportedPermissions;

import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.db.Beacon;

@ExtendWith(MockitoExtension.class)
class HateoasLinkManagerTest {

    @Mock
    BeaconRolesService beaconRolesService;

    @Test
    void buildGetForBeaconShouldReturnExpectedLink() {
        var beacon = new Beacon();
        var beaconId = UUID.randomUUID();
        beacon.setId(beaconId);

        var linkBuilder = new HateoasLinkManager<Beacon>();
        var result = linkBuilder.getLinksFor(
                beacon,
                new BeaconLinkStrategy(beaconRolesService)
        );

        assertThat(result.size(), is(1));
        assertThat(result.get(0).getVerb(), is("GET"));
        assertThat(result.get(0).getPath(), is("/beacons/" + beacon.getId()));
    }

    @Test
    void buildPatchForBeaconShouldReturnExpectedLink() {
        var beacon = new Beacon();
        var beaconId = UUID.randomUUID();
        beacon.setId(beaconId);

        var userRoles = new ArrayList<SupportedPermissions>();

        userRoles.add(SupportedPermissions.APPROLE_UPDATE_RECORDS);
        given(beaconRolesService.getUserRoles()).willReturn(userRoles);

        var linkBuilder = new HateoasLinkManager<Beacon>();
        var result = linkBuilder.getLinksFor(
                beacon,
                new BeaconLinkStrategy(beaconRolesService)
        );

        assertThat(result.size(), is(2));
        assertThat(result.get(0).getVerb(), is("GET"));
        assertThat(result.get(0).getPath(), is("/beacons/" + beacon.getId()));
        assertThat(result.get(1).getVerb(), is("PATCH"));
        assertThat(result.get(1).getPath(), is("/beacons/" + beacon.getId()));
    }
}
