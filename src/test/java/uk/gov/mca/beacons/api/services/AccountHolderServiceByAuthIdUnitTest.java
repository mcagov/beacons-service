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
class AccountHolderServiceByAuthIdUnitTest {

  @Mock
  private AccountHolderGateway mockAccountHolderGateway;

  @Mock
  private AccountHolder mockAccountHolder;

  @Test
  void getByAuthId_shouldReturnNullResultsIfNotFound() {
    AccountHolderService accountHolderService = new AccountHolderService(
      mockAccountHolderGateway
    );
    String nonExistentAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderGateway.getByAuthId(nonExistentAuthId))
      .willReturn(null);

    assertNull(accountHolderService.getByAuthId(nonExistentAuthId));
  }

  @Test
  void getByAuthId_shouldReturnTheAccountHolderByAuthId() {
    AccountHolderService accountHolderService = new AccountHolderService(
      mockAccountHolderGateway
    );
    String existingAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderGateway.getByAuthId(existingAuthId))
      .willReturn(mockAccountHolder);

    AccountHolder foundAccountHolder = accountHolderService.getByAuthId(
      existingAuthId
    );

    assertThat(foundAccountHolder, is(mockAccountHolder));
  }
}
