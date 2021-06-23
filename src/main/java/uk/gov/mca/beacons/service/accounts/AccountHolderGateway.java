package uk.gov.mca.beacons.service.accounts;

import java.util.UUID;
import uk.gov.mca.beacons.service.domain.AccountHolder;

public interface AccountHolderGateway {
  AccountHolder getById(UUID id);

  AccountHolder getByAuthId(String authId);

  AccountHolder save(CreateAccountHolderRequest createAccountHolderRequest);
}
