package uk.gov.mca.beacons.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.gateways.AuthGateway;

@Service
public class GetUserService {

  private final AuthGateway authGateway;

  @Autowired
  public GetUserService(AuthGateway authGateway) {
    this.authGateway = authGateway;
  }

  public User getUser() {
    return authGateway.getUser();
  }
}
