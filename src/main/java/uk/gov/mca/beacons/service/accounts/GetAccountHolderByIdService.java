package uk.gov.mca.beacons.service.accounts;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.service.model.AccountHolder;

@Service
@Transactional
public class GetAccountHolderByIdService {

  public AccountHolder execute(UUID id) {
    return new AccountHolder();
  }
}
