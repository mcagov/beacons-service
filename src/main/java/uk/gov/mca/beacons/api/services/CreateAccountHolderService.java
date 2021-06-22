package uk.gov.mca.beacons.api.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.entities.AccountHolder;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@Service
@Transactional
public class CreateAccountHolderService {

  private final AccountHolderGateway accountHolderGateway;

  @Autowired
  public CreateAccountHolderService(AccountHolderGateway accountHolderGateway) {
    this.accountHolderGateway = accountHolderGateway;
  }

  public AccountHolder execute(
    CreateAccountHolderRequest accountHolderRequest
  ) {
    return accountHolderGateway.save(accountHolderRequest);
  }
}
