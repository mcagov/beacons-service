package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;

public interface AccountHolderGateway
  extends CrudGateway<AccountHolder, CreateAccountHolderRequest> {
  AccountHolder getById(UUID id);

  AccountHolder getByAuthId(String authId);
}
