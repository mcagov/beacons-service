package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.BackOfficeUser;

@ExtendWith(MockitoExtension.class)
class UserGatewayImplUnitTest {

  @InjectMocks
  private UserGatewayImpl userGateway;

  @Mock
  private AccountHolderGateway accountHolderGateway;

  @Mock
  private AuthGateway authGateway;

  @Test
  void shouldReturnTheUserObjectFromTheAccountHolderGateway() {
    final UUID id = UUID.randomUUID();
    given(accountHolderGateway.getById(id))
      .willReturn(
        AccountHolder
          .builder()
          .authId("0fd158e9-d648-4b11-88d9-7bc57080aa5e")
          .fullName("Account Holder")
          .email("account.holder@beaconsstore.gov.uk")
          .build()
      );

    final var user = userGateway.getUserById(id);
    assertThat(user.getAuthId(), is("0fd158e9-d648-4b11-88d9-7bc57080aa5e"));
    assertThat(user.getFullName(), is("Account Holder"));
    assertThat(user.getEmail(), is("account.holder@beaconsstore.gov.uk"));
  }

  @Test
  void shouldReturnTheUserFromTheAuthGatewayIfNoneExistsFromTheAccountHolderGateway() {
    final UUID id = UUID.randomUUID();
    given(accountHolderGateway.getById(id)).willReturn(null);
    given(authGateway.getUser())
      .willReturn(
        BackOfficeUser
          .builder()
          .authId("0fd158e9-d648-4b11-88d9-7bc57080aa5d")
          .fullName("Back Office Crew")
          .email("backoffice@mcgca.gov.uk")
          .build()
      );

    final var user = userGateway.getUserById(id);
    assertThat(user.getAuthId(), is("0fd158e9-d648-4b11-88d9-7bc57080aa5d"));
    assertThat(user.getFullName(), is("Back Office Crew"));
    assertThat(user.getEmail(), is("backoffice@mcgca.gov.uk"));
  }
}
