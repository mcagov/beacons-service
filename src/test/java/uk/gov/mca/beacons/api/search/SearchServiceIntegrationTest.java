package uk.gov.mca.beacons.api.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.TestTransaction;
import uk.gov.mca.beacons.api.BaseIntegrationTest;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolder;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderRepository;
import uk.gov.mca.beacons.api.accountholder.domain.events.AccountHolderUpdated;
import uk.gov.mca.beacons.api.search.documents.AccountHolderDocument;
import uk.gov.mca.beacons.api.search.repositories.AccountHolderSearchRepository;

public class SearchServiceIntegrationTest extends BaseIntegrationTest {

  @Autowired
  ApplicationEventPublisher publisher;

  @MockBean
  AccountHolderSearchRepository accountHolderSearchRepository;

  @MockBean
  AccountHolderRepository accountHolderRepository;

  @Test
  @Transactional
  @DisplayName(
    "When an AccountHolderUpdated event is received, should publish AccountHolderDocument to Opensearch"
  )
  public void updateAccountHolderDocument() {
    // Set up
    AccountHolderId accountHolderId = new AccountHolderId(UUID.randomUUID());
    AccountHolder accountHolder = mock(AccountHolder.class);
    given(accountHolder.getId()).willReturn(accountHolderId);
    given(accountHolder.getEmail()).willReturn("test@test.com");
    given(accountHolder.getFullName()).willReturn("Test Testsson");
    given(accountHolderRepository.findById(accountHolderId))
      .willReturn(Optional.of(accountHolder));
    ArgumentCaptor<AccountHolderDocument> captor = ArgumentCaptor.forClass(
      AccountHolderDocument.class
    );

    // Act
    publisher.publishEvent(new AccountHolderUpdated(accountHolder));

    // Force test transaction to commit so that TransactionalEventListener will fire
    TestTransaction.flagForCommit();
    TestTransaction.end();

    // Assert
    verify(accountHolderSearchRepository, times(1)).save(captor.capture());
    AccountHolderDocument accountHolderDocument = captor.getValue();
    assertThat(
      accountHolderDocument.getId(),
      equalTo(accountHolderId.unwrap())
    );
    assertThat(accountHolderDocument.getEmail(), equalTo("test@test.com"));
    assertThat(accountHolderDocument.getName(), equalTo("Test Testsson"));
  }
}
