package uk.gov.mca.beacons.api.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.entities.AccountHolder;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@Service
@Transactional
public class GetAccountHolderByIdService {

    private final AccountHolderGateway accountHolderGateway;

    @Autowired
    public GetAccountHolderByIdService(
            AccountHolderGateway accountHolderGateway
    ) {
        this.accountHolderGateway = accountHolderGateway;
    }

    public AccountHolder execute(UUID id) {
        return accountHolderGateway.getById(id);
    }
}
