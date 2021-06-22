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
import uk.gov.mca.beacons.api.entities.AccountHolder;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@ExtendWith(MockitoExtension.class)
class GetAccountHolderByAuthIdServiceUnitTest {

  @Mock
  private AccountHolderGateway mockAccountHolderGateway;

  @Mock
  private AccountHolder mockAccountHolder;

  @Test
  void getByAuthId_shouldReturnNullResultsIfNotFound() {
    GetAccountHolderByAuthIdService getAccountHolderByAuthIdService = new GetAccountHolderByAuthIdService(
      mockAccountHolderGateway
    );
    String nonExistentAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderGateway.getByAuthId(nonExistentAuthId))
      .willReturn(null);

    assertNull(getAccountHolderByAuthIdService.execute(nonExistentAuthId));
  }

  @Test
  void getByAuthId_shouldReturnTheAccountHolderByAuthId() {
    GetAccountHolderByAuthIdService getAccountHolderByAuthIdService = new GetAccountHolderByAuthIdService(
      mockAccountHolderGateway
    );
    String existingAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderGateway.getByAuthId(existingAuthId))
      .willReturn(mockAccountHolder);

    AccountHolder foundAccountHolder = getAccountHolderByAuthIdService.execute(
      existingAuthId
    );

    assertThat(foundAccountHolder, is(mockAccountHolder));
  }
}
