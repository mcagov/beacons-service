package uk.gov.mca.beacons.service.accounts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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
class GetAccountHolderByIdServiceUnitTest {

  @Mock
  private AccountHolderRepository mockAccountHolderRepository;

  @Mock
  private AccountHolder mockAccountHolder;

  @Test
  void getById_shouldReturnNullIfNotFound() {
    GetAccountHolderByIdService getAccountHolderByIdService = new GetAccountHolderByIdService(
      mockAccountHolderRepository
    );
    UUID nonExistentAccountHolderId = UUID.randomUUID();
    given(mockAccountHolderRepository.getById(nonExistentAccountHolderId))
      .willReturn(null);

    assertNull(getAccountHolderByIdService.execute(nonExistentAccountHolderId));
  }

  @Test
  void getById_shouldReturnTheAccountHolder() {
    GetAccountHolderByIdService getAccountHolderByIdService = new GetAccountHolderByIdService(
      mockAccountHolderRepository
    );
    UUID existingAuthId = UUID.randomUUID();
    given(mockAccountHolderRepository.getById(existingAuthId))
      .willReturn(mockAccountHolder);

    AccountHolder foundAccountHolder = getAccountHolderByIdService.execute(
      existingAuthId
    );

    assertThat(foundAccountHolder, is(mockAccountHolder));
  }
}
