package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.entities.AccountHolder;

public interface AccountHolderGateway {
  AccountHolder getById(UUID id);

  AccountHolder getByAuthId(String authId);

  AccountHolder save(CreateAccountHolderRequest createAccountHolderRequest);
}
