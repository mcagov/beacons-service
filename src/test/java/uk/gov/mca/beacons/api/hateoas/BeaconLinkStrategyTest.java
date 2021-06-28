package uk.gov.mca.beacons.api.hateoas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static uk.gov.mca.beacons.api.hateoas.BeaconRolesService.SupportedPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@ExtendWith(MockitoExtension.class)
class BeaconLinkStrategyTest {

  @Mock
  BeaconRolesService beaconRolesService;

  Beacon beacon;
  List<SupportedPermissions> userRoles;
  BeaconLinkStrategy linkStrategy;

  @BeforeEach
  void beforeEach() {
    beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);

    userRoles = new ArrayList<SupportedPermissions>();

    linkStrategy = new BeaconLinkStrategy(beaconRolesService);
  }

  @Test
  void buildGetForBeaconShouldReturnExpectedLink() {
    var result = linkStrategy.getGetPath(beacon);

    assertThat(result, is("/beacons/" + beacon.getId()));
  }

  @Test
  void buildPatchForBeaconShouldReturnExpectedLink() {
    var result = linkStrategy.getPatchPath(beacon);

    assertThat(result, is("/beacons/" + beacon.getId()));
  }

  @Test
  void checkPermissionForPatchShouldReturnTrueWhenRoleIsPresent() {
    userRoles.add(SupportedPermissions.APPROLE_UPDATE_RECORDS);
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.userCanPatchEntity(beacon);

    assertThat(result, is(true));
  }

  @Test
  void checkPermissionForPatchShouldReturnFalseWhenRoleNotPresent() {
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.userCanPatchEntity(beacon);

    assertThat(result, is(false));
  }
}
