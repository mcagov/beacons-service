package uk.gov.mca.beacons.api.accountholder.application;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolder;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderRepository;

@Transactional
@Service("AccountHolderServiceV2")
public class AccountHolderService {

  private final AccountHolderRepository accountHolderRepository;

  @Autowired
  public AccountHolderService(AccountHolderRepository accountHolderRepository) {
    this.accountHolderRepository = accountHolderRepository;
  }

  public AccountHolder create(AccountHolder accountHolder) {
    return accountHolderRepository.save(accountHolder);
  }

  public Optional<AccountHolder> getAccountHolder(AccountHolderId id) {
    return accountHolderRepository.findById(id);
  }

  public Optional<AccountHolder> getAccountHolderByAuthId(String authId) {
    return accountHolderRepository.findAccountHolderByAuthId(authId);
  }
}
