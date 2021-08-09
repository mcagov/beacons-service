package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.User;

@Repository
@Transactional
public class UserGatewayImpl implements UserGateway {

  private final AccountHolderGateway accountHolderGateway;
  private final AuthGateway authGateway;

  @Autowired
  public UserGatewayImpl(
    AccountHolderGateway accountHolderGateway,
    AuthGateway authGateway
  ) {
    this.accountHolderGateway = accountHolderGateway;
    this.authGateway = authGateway;
  }

  /**
   * A user performing an action within beacons could be either an account holder or a backoffice user.
   * This gateway encapsulates the logic for retrieving a either user and returns a {@link User} domain object.
   *
   * @param id The id of the user
   * @return The user domain object
   */
  @Override
  public User getUserById(UUID id) {
    final var accountHolderUser = accountHolderGateway.getById(id);

    if (accountHolderUser != null) {
      return accountHolderUser;
    }

    return authGateway.getUser();
  }
}
