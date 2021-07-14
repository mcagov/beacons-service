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

  @Override
  public User getUserById(UUID id) {
    final var accountHolderUser = accountHolderGateway.getById(id);

    if (accountHolderUser != null) {
      return accountHolderUser;
    }

    return authGateway.getUser();
  }
}
