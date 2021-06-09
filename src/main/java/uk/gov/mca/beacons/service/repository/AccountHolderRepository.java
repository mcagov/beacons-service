package uk.gov.mca.beacons.service.repository;

import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.AccountHolder;

@Repository
public class AccountHolderRepository {

  public AccountHolder getByAuthId(String authId) {
    return new AccountHolder();
  }
}
