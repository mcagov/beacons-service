package uk.gov.mca.beacons.service.hateoas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static uk.gov.mca.beacons.service.hateoas.BeaconRolesService.SupportedPermissions;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class BeaconRolesServiceTest {

  SimpleGrantedAuthority updaterAuth = new SimpleGrantedAuthority(
    SupportedPermissions.APPROLE_UPDATE_RECORDS.toString()
  );
  SimpleGrantedAuthority unsupportedAuth1 = new SimpleGrantedAuthority(
    "JUNK_AUTH_1"
  );
  SimpleGrantedAuthority unsupportedAuth2 = new SimpleGrantedAuthority(
    "JUNK_AUTH_2"
  );

  Authentication authentication = mock(Authentication.class);
  SecurityContext context = mock(SecurityContext.class);

  @BeforeEach
  void before() {
    given(context.getAuthentication()).willReturn(authentication);
    SecurityContextHolder.setContext(context);
  }

  @Test
  void ShouldReturnNoPermissionsForNullAuth() {
    given(context.getAuthentication()).willReturn(null);

    var rolesService = new BeaconRolesService();
    var roles = rolesService.getUserRoles();

    assertThat(roles, is(empty()));
  }

  @Test
  void ShouldReturnNoPermissionsForRolelessUser() {
    var rolesService = new BeaconRolesService();
    var roles = rolesService.getUserRoles();

    assertThat(roles, is(empty()));
  }

  @Test
  void ShouldReturnPermissionForUpdateRecordsUser() {
    doReturn(List.of(updaterAuth)).when(authentication).getAuthorities();

    var rolesService = new BeaconRolesService();
    var roles = rolesService.getUserRoles();

    assertThat(roles.size(), is(1));
  }

  @Test
  void ShouldReturnPermissionForUpdateRecordsUserAndIgnoreUnsupportedPermissions() {
    doReturn(List.of(unsupportedAuth1, updaterAuth, unsupportedAuth2))
      .when(authentication)
      .getAuthorities();

    var rolesService = new BeaconRolesService();
    var roles = rolesService.getUserRoles();

    assertThat(roles.size(), is(1));
  }

  @Test
  void ShouldReturnIgnoreUnsupportedPermissions() {
    doReturn(List.of(unsupportedAuth1, unsupportedAuth2))
      .when(authentication)
      .getAuthorities();

    var rolesService = new BeaconRolesService();
    var roles = rolesService.getUserRoles();

    assertThat(roles, is(empty()));
  }
}
