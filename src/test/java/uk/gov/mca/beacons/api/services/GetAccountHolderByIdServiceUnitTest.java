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
class GetAccountHolderByIdServiceUnitTest {

    @Mock
    private AccountHolderGateway mockAccountHolderGateway;

    @Mock
    private AccountHolder mockAccountHolder;

    @Test
    void getById_shouldReturnNullIfNotFound() {
        GetAccountHolderByIdService getAccountHolderByIdService = new GetAccountHolderByIdService(
                mockAccountHolderGateway
        );
        UUID nonExistentAccountHolderId = UUID.randomUUID();
        given(mockAccountHolderGateway.getById(nonExistentAccountHolderId))
                .willReturn(null);

        assertNull(getAccountHolderByIdService.execute(nonExistentAccountHolderId));
    }

    @Test
    void getById_shouldReturnTheAccountHolder() {
        GetAccountHolderByIdService getAccountHolderByIdService = new GetAccountHolderByIdService(
                mockAccountHolderGateway
        );
        UUID existingAuthId = UUID.randomUUID();
        given(mockAccountHolderGateway.getById(existingAuthId))
                .willReturn(mockAccountHolder);

        AccountHolder foundAccountHolder = getAccountHolderByIdService.execute(
                existingAuthId
        );

        assertThat(foundAccountHolder, is(mockAccountHolder));
    }
}
