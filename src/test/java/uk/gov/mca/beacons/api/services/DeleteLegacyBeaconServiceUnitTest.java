package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.dto.DeleteLegacyBeaconRequestDTO;
import uk.gov.mca.beacons.api.exceptions.UserNotFoundException;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.gateways.NoteGateway;
import uk.gov.mca.beacons.api.gateways.UserGateway;

@ExtendWith(MockitoExtension.class)
class DeleteLegacyBeaconServiceUnitTest {

  private DeleteLegacyBeaconRequestDTO request;

  @InjectMocks
  private DeleteLegacyBeaconService deleteLegacyBeaconService;

  @Mock
  private LegacyBeaconGateway legacyBeaconGateway;

  @Mock
  private UserGateway userGateway;

  @Mock
  private NoteGateway noteGateway;

  @Captor
  private ArgumentCaptor<Note> noteCaptor;

  @BeforeEach
  void init() {
    request =
      DeleteLegacyBeaconRequestDTO
        .builder()
        .legacyBeaconId(UUID.randomUUID())
        .userId(UUID.randomUUID())
        .reason("I do not recognise this beacon")
        .build();
  }

  @Test
  void shouldCallTheLegacyBeaconGatewayToDeleteTheLegacyBeacon() {
    given(userGateway.getUserById(request.getUserId()))
      .willReturn(new AccountHolder());

    deleteLegacyBeaconService.delete(request);

    verify(legacyBeaconGateway, times(1)).delete(request.getLegacyBeaconId());
  }

  @Test
  void shouldNotDeleteTheLegacyBeaconOrCreateTheNoteIfAUserCannotBeFound() {
    given(userGateway.getUserById(request.getUserId())).willReturn(null);

    assertThrows(
      UserNotFoundException.class,
      () -> {
        deleteLegacyBeaconService.delete(request);
      }
    );
    verify(legacyBeaconGateway, never()).delete(request.getLegacyBeaconId());
    verify(noteGateway, never()).create(any(Note.class));
  }

  @Test
  void shouldCallTheNoteGatewayToCreateTheNote() {
    final var accountHolder = AccountHolder
      .builder()
      .email("bbeacon@beacons.com")
      .fullName("Mr B. Beacon")
      .build();

    given(userGateway.getUserById(request.getUserId()))
      .willReturn(accountHolder);

    deleteLegacyBeaconService.delete(request);

    verify(noteGateway).create(noteCaptor.capture());
    final var note = noteCaptor.getValue();

    assertThat(note.getLegacyBeaconId(), is(request.getLegacyBeaconId()));
    assertThat(note.getUserId(), is(request.getUserId()));
    assertThat(note.getType(), is(NoteType.RECORD_HISTORY));
    assertThat(
      note.getText(),
      is(
        "The account holder deleted the record with reason: '" +
        "I do not recognise this beacon" +
        "'"
      )
    );
  }
}
