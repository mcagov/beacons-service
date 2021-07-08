package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CurrentBackOfficeUserGatewayImplUnitTest {

  @Test
  void shouldReturnTheCurrentUser() {
    final var currentUserGateway = new CurrentUserGatewayImpl();

    final var user = currentUserGateway.getCurrentUser();
    //        assertThat(user.getId(), is());
  }
}
