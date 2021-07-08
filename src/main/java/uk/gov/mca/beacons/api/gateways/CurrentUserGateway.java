package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.domain.User;

public interface CurrentUserGateway {
  User getCurrentUser();
}
