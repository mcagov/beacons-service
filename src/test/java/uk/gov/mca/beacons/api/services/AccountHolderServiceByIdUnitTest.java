package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@ExtendWith(MockitoExtension.class)
class AccountHolderServiceByIdUnitTest {

  @Mock
  private AccountHolderGateway mockAccountHolderGateway;

  @Mock
  private AccountHolder mockAccountHolder;

  @Test
  void getById_shouldReturnNullIfNotFound() {
    AccountHolderService accountHolderService = new AccountHolderService(
      mockAccountHolderGateway
    );
    UUID nonExistentAccountHolderId = UUID.randomUUID();
    given(mockAccountHolderGateway.getById(nonExistentAccountHolderId))
      .willReturn(null);

    assertNull(accountHolderService.getById(nonExistentAccountHolderId));
  }

  @Test
  void getById_shouldReturnTheAccountHolder() {
    AccountHolderService accountHolderService = new AccountHolderService(
      mockAccountHolderGateway
    );
    UUID accountId = UUID.randomUUID();
    given(mockAccountHolderGateway.getById(accountId))
      .willReturn(mockAccountHolder);

    AccountHolder foundAccountHolder = accountHolderService.getById(accountId);

    assertThat(foundAccountHolder, is(mockAccountHolder));
  }
}
