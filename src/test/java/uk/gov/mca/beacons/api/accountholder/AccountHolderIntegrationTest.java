package uk.gov.mca.beacons.api.accountholder;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolder;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderRepository;
import uk.gov.mca.beacons.api.shared.domain.person.Address;

@SpringBootTest
public class AccountHolderIntegrationTest {

  @Autowired
  AccountHolderRepository accountHolderRepository;

  @Transactional
  @Test
  public void shouldSaveAccountHolder() {
    AccountHolder accountHolder = new AccountHolder();
    accountHolder.setFullName("John");
    accountHolder.setAddress(
      Address.builder().addressLine1("Something").build()
    );
    accountHolder.setEmail("test@test.com");

    AccountHolder savedAccountHolder = accountHolderRepository.saveAndFlush(
      accountHolder
    );

    assert savedAccountHolder.getId() != null;
    assert savedAccountHolder.getCreatedDate() != null;
    assert savedAccountHolder.getLastModifiedDate() != null;
    assert savedAccountHolder.getAddress() != null;
  }
}
