package uk.gov.mca.beacons.api.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.gateways.AuthGateway;
import uk.gov.mca.beacons.api.gateways.UserGateway;

@Service
public class GetUserService {

  private final UserGateway userGateway;

  @Autowired
  public GetUserService(UserGateway userGateway) {
    this.userGateway = userGateway;
  }

  public User getUser(UUID id) {
    return userGateway.getUserById(id);
  }
}
