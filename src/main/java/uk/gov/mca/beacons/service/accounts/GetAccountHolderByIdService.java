package uk.gov.mca.beacons.service.accounts;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;

@Service
@Transactional
public class GetAccountHolderByIdService {

    private final AccountHolderRepository accountHolderRepository;

    public GetAccountHolderByIdService(
            AccountHolderRepository accountHolderRepository
    ) {
        this.accountHolderRepository = accountHolderRepository;
    }

    public AccountHolder execute(UUID id) {
        return accountHolderRepository.getById(id);
    }
}
