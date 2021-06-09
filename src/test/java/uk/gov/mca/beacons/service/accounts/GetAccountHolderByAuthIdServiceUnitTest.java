package uk.gov.mca.beacons.service.accounts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;

@ExtendWith(MockitoExtension.class)
class GetAccountHolderByAuthIdServiceUnitTest {

  @Mock
  private AccountHolderRepository mockAccountHolderRepository;

  @Mock
  private AccountHolder mockAccountHolder;

  @Test
  void getByAuthId_shouldReturnNullResultsIfNotFound() {
    GetAccountHolderByAuthIdService getAccountHolderByAuthIdService = new GetAccountHolderByAuthIdService(
      mockAccountHolderRepository
    );
    String nonExistentAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderRepository.getByAuthId(nonExistentAuthId))
      .willReturn(null);

    assertNull(getAccountHolderByAuthIdService.execute(nonExistentAuthId));
  }

  @Test
  void getByAuthId_shouldReturnTheAccountHolderByAuthId() {
    GetAccountHolderByAuthIdService getAccountHolderByAuthIdService = new GetAccountHolderByAuthIdService(
      mockAccountHolderRepository
    );
    String existingAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderRepository.getByAuthId(existingAuthId))
      .willReturn(mockAccountHolder);

    AccountHolder foundAccountHolder = getAccountHolderByAuthIdService.execute(
      existingAuthId
    );

    assertThat(foundAccountHolder, equalTo(mockAccountHolder));
  }
}
