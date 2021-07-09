package uk.gov.mca.beacons.api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;
import uk.gov.mca.beacons.api.gateways.NoteGateway;

@ExtendWith(MockitoExtension.class)
class DeleteBeaconServiceUnitTest {

  @InjectMocks
  private DeleteBeaconService deleteBeaconService;

  @Mock
  private BeaconGateway beaconGateway;

  @Mock
  private AccountHolderGateway accountHolderGateway;

  @Mock
  private NoteGateway noteGateway;

  @Test
  void shouldCallTheBeaconGatewayToDeleteTheBeacon() {
    final var request = DeleteBeaconRequestDTO
      .builder()
      .beaconId(UUID.randomUUID())
      .accountHolderId(UUID.randomUUID())
      .reason("Unused on my boat anymore")
      .build();

    deleteBeaconService.delete(request);

    verify(beaconGateway, times(1)).delete(request.getBeaconId());
  }
}
