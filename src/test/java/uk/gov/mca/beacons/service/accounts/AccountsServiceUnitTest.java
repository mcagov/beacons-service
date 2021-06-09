package uk.gov.mca.beacons.service.accounts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class AccountsServiceUnitTest {

  @Mock
  private AccountHolderRepository mockAccountHolderRepository;

  @Mock
  private AccountHolder mockAccountHolder;

  @Test
  void getByAuthId_shouldReturnNullResultsIfNotFound() {
    AccountsService accountsService = new AccountsService(mockAccountHolderRepository);
    String nonExistentAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderRepository
            .getByAuthId(nonExistentAuthId))
            .willReturn(null);

    assertNull(accountsService.getByAuthId(
            nonExistentAuthId
    ));
  }

  @Test
  void getByAuthId_shouldReturnTheAccountHolderByAuthId() {
    AccountsService accountsService = new AccountsService(mockAccountHolderRepository);
    String existingAuthId = UUID.randomUUID().toString();
    given(mockAccountHolderRepository
            .getByAuthId(existingAuthId))
            .willReturn(mockAccountHolder);

    AccountHolder foundAccountHolder = accountsService.getByAuthId(existingAuthId);

    assertEquals(foundAccountHolder, mockAccountHolder);
  }
}
