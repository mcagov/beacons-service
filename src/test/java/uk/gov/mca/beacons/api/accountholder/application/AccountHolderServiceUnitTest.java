package uk.gov.mca.beacons.api.accountholder.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolder;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderRepository;
import uk.gov.mca.beacons.api.accountholder.domain.events.AccountHolderCreated;

@ExtendWith(MockitoExtension.class)
public class AccountHolderServiceUnitTest {

  @Mock
  ApplicationEventPublisher applicationEventPublisher;

  @Mock
  AccountHolderRepository accountHolderRepository;

  @InjectMocks
  AccountHolderService accountHolderService;

  @Test
  void shouldPublishAccountHolderCreatedEventWhenAccountHolderIsCreated() {
    AccountHolder savedAccountHolder = mock(AccountHolder.class);

    given(savedAccountHolder.getId())
      .willReturn(new AccountHolderId(UUID.randomUUID()));

    given(accountHolderRepository.save(any(AccountHolder.class)))
      .willReturn(savedAccountHolder);

    accountHolderService.create(new AccountHolder());

    then(applicationEventPublisher)
      .should(times(1))
      .publishEvent(isA(AccountHolderCreated.class));
  }
}
