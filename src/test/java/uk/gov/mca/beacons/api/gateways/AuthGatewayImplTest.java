package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.azure.spring.aad.webapi.AADOAuth2AuthenticatedPrincipal;
import java.util.HashMap;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.gov.mca.beacons.api.domain.User;

@ExtendWith(MockitoExtension.class)
class AuthGatewayImplTest {

  Authentication authentication = mock(Authentication.class);
  SecurityContext context = mock(SecurityContext.class);
  AADOAuth2AuthenticatedPrincipal principal = mock(
    AADOAuth2AuthenticatedPrincipal.class
  );

  @BeforeEach
  void before() {
    given(context.getAuthentication()).willReturn(authentication);
    given(authentication.getPrincipal()).willReturn(principal);
    SecurityContextHolder.setContext(context);
  }

  @Nested
  class GetUser {

    @Test
    void getsUserAttributesAndCreatesUser() {
      final UUID authId = UUID.randomUUID();
      final String fullName = "Marie Antoinette";
      final String email = "let.them.eat@cake.fr";

      final var mockPrincipal = new HashMap<String, Object>();
      mockPrincipal.put("oid", authId.toString());
      mockPrincipal.put("name", fullName);
      mockPrincipal.put("email", email);
      given(principal.getAttributes()).willReturn(mockPrincipal);

      AuthGateway authGateway = new AuthGatewayImpl();
      User user = authGateway.getUser();

      assertThat(user.getAuthId(), is(authId));
      assertThat(user.getFullName(), is(fullName));
      assertThat(user.getEmail(), is(email));
    }
  }
}
