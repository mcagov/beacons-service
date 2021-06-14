package uk.gov.mca.beacons.service.accounts;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;

@Service
@Transactional
public class CreateAccountHolderService {

  private final AccountHolderRepository accountHolderRepository;

  @Autowired
  public CreateAccountHolderService(
    AccountHolderRepository accountHolderRepository
  ) {
    this.accountHolderRepository = accountHolderRepository;
  }

  public AccountHolder execute(AccountHolder accountHolder) {
    return accountHolderRepository.save(accountHolder);
  }
}
