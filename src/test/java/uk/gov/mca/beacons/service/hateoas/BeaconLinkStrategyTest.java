package uk.gov.mca.beacons.service.hateoas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.Beacon;

@ExtendWith(MockitoExtension.class)
class BeaconLinkStrategyTest {

  @Mock
  BeaconRolesService beaconRolesService;

  Beacon beacon;
  ArrayList<String> userRoles;
  BeaconLinkStrategy linkStrategy;

  @BeforeEach
  void beforeEach() {
    beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);

    userRoles = new ArrayList<String>();

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
    userRoles.add("APPROLE_UPDATE_RECORDS");
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.checkPatchPermission(beacon);

    assertThat(result, is(true));
  }

  @Test
  void checkPermissionForPatchShouldReturnFalseWhenRoleNotPresent() {
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.checkPatchPermission(beacon);

    assertThat(result, is(false));
  }
}
