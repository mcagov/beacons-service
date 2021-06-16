package uk.gov.mca.beacons.service.accounts;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.service.model.AccountHolder;

import java.util.UUID;

@Service
@Transactional
public class GetAccountHolderByIdService {
    public AccountHolder execute(UUID id) {
        return new AccountHolder();
    }
}
