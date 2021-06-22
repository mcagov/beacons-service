package uk.gov.mca.beacons.api.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.entities.AccountHolder;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@Service
@Transactional
public class GetAccountHolderByAuthIdService {

    private final AccountHolderGateway accountHolderGateway;

    @Autowired
    public GetAccountHolderByAuthIdService(
            AccountHolderGateway accountHolderGateway
    ) {
        this.accountHolderGateway = accountHolderGateway;
    }

    public AccountHolder execute(String authId) {
        return accountHolderGateway.getByAuthId(authId);
    }
}
