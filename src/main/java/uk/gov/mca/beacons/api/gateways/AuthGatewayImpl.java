package uk.gov.mca.beacons.api.gateways;

import com.azure.spring.aad.webapi.AADOAuth2AuthenticatedPrincipal;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uk.gov.mca.beacons.api.domain.User;

public class AuthGatewayImpl implements AuthGateway {

  public User getUser() {
    final var authentication = getAuthentication();
    final var user = (AADOAuth2AuthenticatedPrincipal) authentication.getPrincipal();
    final var userAttributes = user.getAttributes();

    //    TODO: how to handle "Beacon Registry" notes
    UUID authId = UUID.fromString((String) userAttributes.get("oid"));
    String name = (String) userAttributes.get("name");
    String email = (String) userAttributes.get("email");

    return User.builder().authId(authId).fullName(name).email(email).build();
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
