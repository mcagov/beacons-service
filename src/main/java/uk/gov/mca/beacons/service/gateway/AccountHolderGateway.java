package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import uk.gov.mca.beacons.service.accounts.CreateAccountHolderRequest;
import uk.gov.mca.beacons.service.entities.AccountHolder;

public interface AccountHolderGateway {
    AccountHolder getById(UUID id);

    AccountHolder getByAuthId(String authId);

    AccountHolder save(CreateAccountHolderRequest createAccountHolderRequest);
}
