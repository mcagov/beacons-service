package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.BackOfficeUser;
import uk.gov.mca.beacons.api.gateways.AuthGateway;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

  @InjectMocks
  private GetUserService getUserService;

  @Mock
  private AuthGateway authGateway;

  @Test
  void shouldReturnTheCurrentUser() {
    final UUID userId = UUID.randomUUID();
    final String name = "Cher Horowitz";
    final String email = "this.is@an.alaia";
    final BackOfficeUser user = BackOfficeUser
      .builder()
      .id(userId)
      .fullName(name)
      .email(email)
      .build();

    given(authGateway.getUser()).willReturn(user);

    assertThat(getUserService.getUser(), is(user));
  }
}
