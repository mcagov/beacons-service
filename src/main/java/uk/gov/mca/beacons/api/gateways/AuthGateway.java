package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.gateways.AuthGatewayImpl.SupportedPermissions;

public interface AuthGateway {
  User getUser();

  List<SupportedPermissions> getUserRoles();
}
