package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@ExtendWith(MockitoExtension.class)
class AccountHolderServiceCreateAccountUnitTest {

  @Mock
  private AccountHolderGateway accountHolderGateway;

  @Test
  void shouldCreateTheAccountHolder() {
    final AccountHolderService accountHolderService = new AccountHolderService(
      accountHolderGateway
    );
    final CreateAccountHolderRequest accountHolder = new CreateAccountHolderRequest();
    final AccountHolder createdAccountHolder = new AccountHolder();

    given(accountHolderGateway.save(accountHolder))
      .willReturn(createdAccountHolder);

    assertThat(
      accountHolderService.create(accountHolder),
      is(createdAccountHolder)
    );
  }
}
