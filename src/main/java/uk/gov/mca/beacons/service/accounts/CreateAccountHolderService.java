package uk.gov.mca.beacons.service.accounts;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.domain.AccountHolder;

@Service
@Transactional
public class CreateAccountHolderService {

    private final AccountHolderGateway accountHolderGateway;

    @Autowired
    public CreateAccountHolderService(
            AccountHolderGateway accountHolderGateway
    ) {
        this.accountHolderGateway = accountHolderGateway;
    }

    public AccountHolder execute(CreateAccountHolderRequest accountHolderRequest) {
        return accountHolderGateway.save(accountHolderRequest);
    }
}
