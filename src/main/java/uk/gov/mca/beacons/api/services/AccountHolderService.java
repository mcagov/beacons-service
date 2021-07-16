package uk.gov.mca.beacons.api.services;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@Service
@Transactional
public class AccountHolderService {

  private final AccountHolderGateway accountHolderGateway;

  @Autowired
  public AccountHolderService(AccountHolderGateway accountHolderGateway) {
    this.accountHolderGateway = accountHolderGateway;
  }

  public AccountHolder create(CreateAccountHolderRequest accountHolderRequest) {
    return accountHolderGateway.create(accountHolderRequest);
  }

  public AccountHolder update(UUID id, AccountHolder accountHolderRequest) {
    return accountHolderGateway.update(id, accountHolderRequest);
  }

  public AccountHolder getByAuthId(String authId) {
    return accountHolderGateway.getByAuthId(authId);
  }

  public AccountHolder getById(UUID id) {
    return accountHolderGateway.getById(id);
  }
}
