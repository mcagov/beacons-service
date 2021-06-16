package uk.gov.mca.beacons.service.accounts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;

@ExtendWith(MockitoExtension.class)
class CreateAccountHolderServiceUnitTest {

  @Mock
  private AccountHolderRepository accountHolderRepository;

  @Test
  void shouldCreateTheAccountHolder() {
    final CreateAccountHolderService createAccountHolderService = new CreateAccountHolderService(
      accountHolderRepository
    );
    final AccountHolder accountHolder = new AccountHolder();
    final AccountHolder createdAccountHolder = new AccountHolder();

    given(accountHolderRepository.save(accountHolder))
      .willReturn(createdAccountHolder);

    assertThat(
      createAccountHolderService.execute(accountHolder),
      is(createdAccountHolder)
    );
  }
}
