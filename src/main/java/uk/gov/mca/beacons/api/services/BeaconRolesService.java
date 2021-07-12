package uk.gov.mca.beacons.api.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.gateways.AuthGateway;
import uk.gov.mca.beacons.api.gateways.AuthGatewayImpl.SupportedPermissions;

@Service
public class BeaconRolesService {

  private AuthGateway authGateway;

  @Autowired
  public BeaconRolesService(AuthGateway authGateway) {
    this.authGateway = authGateway;
  }

  public List<SupportedPermissions> getUserRoles() {
    return authGateway.getUserRoles();
  }
}
