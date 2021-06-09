package uk.gov.mca.beacons.service.accounts;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;

@Service
public class AccountsService {

  private final AccountHolderRepository accountHolderRepository;

  public AccountsService(AccountHolderRepository accountHolderRepository) {
    this.accountHolderRepository = accountHolderRepository;
  }

  public AccountHolder getByAuthId(String authId) {
    return accountHolderRepository.getByAuthId(authId);
  }
}
