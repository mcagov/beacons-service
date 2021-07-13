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
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.gateways.AuthGateway;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

  @InjectMocks
  private GetUserService getUserService;

  @Mock
  private AuthGateway mockAuthGateway;

  @Test
  void shouldReturnTheCurrentUser() {
    final UUID authId = UUID.randomUUID();
    final String name = "Cher Horowitz";
    final String email = "this.is@an.alaia";
    final User user = User
      .builder()
      .authId(authId)
      .fullName(name)
      .email(email)
      .build();

    given(mockAuthGateway.getUser()).willReturn(user);

    assertThat(getUserService.getUser(), is(user));
  }
}
