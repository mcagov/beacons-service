package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;

@ExtendWith(MockitoExtension.class)
class AccountHolderServiceTest {

  @Mock
  private AccountHolderGateway mockAccountHolderGateway;

  @Mock
  private AccountHolder mockAccountHolder;

  @Test
  void create_shouldCreateTheAccountHolder() {
    final AccountHolderService accountHolderService = new AccountHolderService(
      mockAccountHolderGateway
    );
    final CreateAccountHolderRequest accountHolder = new CreateAccountHolderRequest();
    final AccountHolder createdAccountHolder = new AccountHolder();

    given(mockAccountHolderGateway.create(accountHolder))
      .willReturn(createdAccountHolder);

    assertThat(
      accountHolderService.create(accountHolder),
      is(createdAccountHolder)
    );
  }

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
