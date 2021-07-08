package uk.gov.mca.beacons.api.gateways;

import com.azure.spring.aad.webapi.AADOAuth2AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.BackOfficeUser;
import uk.gov.mca.beacons.api.domain.User;

@Service
public class CurrentUserGatewayImpl implements CurrentUserGateway {

  private static final String AZURE_AD_ID_ATTRIBUTE_KEY = "oid";
  private static final String AZURE_AD_FULL_NAME_ATTRIBUTE_KEY = "name";
  private static final String AZURE_AD_EMAIL_ATTRIBUTE_KEY = "email";

  /**
   * Constructs a user domain object from the current security context.
   * <p>
   * See Microsoft docs for information on attribute that are encoded within the JWT.
   * https://docs.microsoft.com/en-us/azure/active-directory/develop/id-tokens
   *
   * @return The user domain object
   */
  @Override
  public User getCurrentUser() {
    final var authentication = (AADOAuth2AuthenticatedPrincipal) SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal();
    final var userAttributes = authentication.getAttributes();

    return BackOfficeUser
      .builder()
      .id((String) userAttributes.get(AZURE_AD_ID_ATTRIBUTE_KEY))
      .fullName((String) userAttributes.get(AZURE_AD_FULL_NAME_ATTRIBUTE_KEY))
      .email((String) userAttributes.get(AZURE_AD_EMAIL_ATTRIBUTE_KEY))
      .build();
  }
}
