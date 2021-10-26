package uk.gov.mca.beacons.api.hateoas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static uk.gov.mca.beacons.api.gateways.AuthGatewayImpl.SupportedPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.services.BeaconRolesService;

@ExtendWith(MockitoExtension.class)
class BeaconUseLinkStrategyTest {

  @Mock
  BeaconRolesService beaconRolesService;

  BeaconUse beaconUse;
  List<SupportedPermissions> userRoles;
  BeaconUseLinkStrategy linkStrategy;

  @BeforeEach
  void beforeEach() {
    beaconUse = new BeaconUse();
    var beaconUseId = UUID.randomUUID();
    beaconUse.setId(beaconUseId);

    userRoles = new ArrayList<>();

    linkStrategy = new BeaconUseLinkStrategy(beaconRolesService);
  }

  @Test
  void buildGetForBeaconShouldReturnExpectedLink() {
    assertThrows(
      NotImplementedException.class,
      () -> linkStrategy.getGetPath(beaconUse)
    );
  }

  @Test
  void buildPatchForBeaconShouldReturnExpectedLink() {
    var result = linkStrategy.getPatchPath(beaconUse);

    assertThat(result, is("/spring-api/beacon-uses/" + beaconUse.getId()));
  }

  @Test
  void checkPermissionForGetShouldReturnFalse() {
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.userCanPatchEntity(beaconUse);

    assertThat(result, is(false));
  }

  @Test
  void checkPermissionForPatchShouldReturnTrueWhenRoleIsPresent() {
    userRoles.add(SupportedPermissions.APPROLE_UPDATE_RECORDS);
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.userCanPatchEntity(beaconUse);

    assertThat(result, is(true));
  }

  @Test
  void checkPermissionForPatchShouldReturnFalseWhenRoleNotPresent() {
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);
    var result = linkStrategy.userCanPatchEntity(beaconUse);

    assertThat(result, is(false));
  }
}
