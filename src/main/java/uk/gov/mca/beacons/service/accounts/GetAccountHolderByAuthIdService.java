package uk.gov.mca.beacons.service.accounts;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.domain.AccountHolder;

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
