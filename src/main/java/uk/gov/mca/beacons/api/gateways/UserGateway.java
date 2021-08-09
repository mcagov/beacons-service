package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import uk.gov.mca.beacons.api.domain.User;

public interface UserGateway {
  User getUserById(UUID id);
}
