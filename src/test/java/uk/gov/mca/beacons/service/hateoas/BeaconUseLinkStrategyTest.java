package uk.gov.mca.beacons.service.hateoas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.UUID;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.BeaconUse;

@ExtendWith(MockitoExtension.class)
class BeaconUseLinkStrategyTest {

  @Mock
  BeaconRolesService beaconRolesService;

  BeaconUse beaconUse;
  ArrayList<String> userRoles;
  BeaconUseLinkStrategy linkStrategy;

  @BeforeEach
  void beforeEach() {
    beaconUse = new BeaconUse();
    var beaconUseId = UUID.randomUUID();
    beaconUse.setId(beaconUseId);

    userRoles = new ArrayList<String>();

    linkStrategy = new BeaconUseLinkStrategy(beaconRolesService);
  }

  @Test
  void buildGetForBeaconShouldReturnExpectedLink() {
    assertThrows(
      NotImplementedException.class,
      () -> {
        linkStrategy.getGetPath(beaconUse);
      }
    );
  }

  @Test
  void buildPatchForBeaconShouldReturnExpectedLink() {
    var result = linkStrategy.getPatchPath(beaconUse);

    assertThat(result, is("/beacon-uses/" + beaconUse.getId()));
  }

  @Test
  void checkPermissionForGetShouldReturnFalse() {
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.checkPatchPermission(beaconUse);

    assertThat(result, is(false));
  }

  @Test
  void checkPermissionForPatchShouldReturnTrueWhenRoleIsPresent() {
    userRoles.add("APPROLE_UPDATE_RECORDS");
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var result = linkStrategy.checkPatchPermission(beaconUse);

    assertThat(result, is(true));
  }

  @Test
  void checkPermissionForPatchShouldReturnFalseWhenRoleNotPresent() {
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);
    var result = linkStrategy.checkPatchPermission(beaconUse);

    assertThat(result, is(false));
  }
}
