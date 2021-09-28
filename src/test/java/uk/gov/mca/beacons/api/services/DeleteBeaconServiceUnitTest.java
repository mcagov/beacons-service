package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;
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
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;
import uk.gov.mca.beacons.api.exceptions.UserNotFoundException;
import uk.gov.mca.beacons.api.gateways.*;

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

  @Mock
  private OwnerGateway ownerGateway;

  @Mock
  private UseGateway useGateway;

  @Mock
  private EmergencyContactGateway emergencyContactGateway;

  @Captor
  private ArgumentCaptor<Note> noteCaptor;

  @Test
  void shouldCallTheBeaconGatewayToDeleteTheBeacon() {
    final var accountHolderId = UUID.randomUUID();
    final var request = DeleteBeaconRequestDTO
      .builder()
      .beaconId(UUID.randomUUID())
      .userId(accountHolderId)
      .reason("Unused on my boat anymore")
      .build();
    given(accountHolderGateway.getById(accountHolderId))
      .willReturn(new AccountHolder());

    deleteBeaconService.delete(request);

    verify(ownerGateway, times(1)).deleteByBeaconId(request.getBeaconId());
    verify(emergencyContactGateway, times(1))
      .deleteAllByBeaconId(request.getBeaconId());
    verify(useGateway, times(1)).deleteAllByBeaconId(request.getBeaconId());
    verify(beaconGateway, times(1)).delete(request.getBeaconId());
  }

  @Test
  void shouldNotDeleteTheBeaconOrCreateTheNoteIfAUserCannotBeFound() {
    final var accountHolderId = UUID.randomUUID();
    final var request = DeleteBeaconRequestDTO
      .builder()
      .beaconId(UUID.randomUUID())
      .userId(accountHolderId)
      .reason("Unused on my boat anymore")
      .build();
    given(accountHolderGateway.getById(accountHolderId)).willReturn(null);

    assertThrows(
      UserNotFoundException.class,
      () -> deleteBeaconService.delete(request)
    );
    verify(beaconGateway, never()).delete(request.getBeaconId());
    verify(noteGateway, never()).create(any(Note.class));
  }

  @Test
  void shouldCallTheNoteGatewayToCreateTheNote() {
    final var accountHolderId = UUID.randomUUID();
    final var beaconId = UUID.randomUUID();
    final var request = DeleteBeaconRequestDTO
      .builder()
      .beaconId(beaconId)
      .userId(accountHolderId)
      .reason("Unused on my boat anymore")
      .build();
    final var accountHolder = AccountHolder
      .builder()
      .email("beacons@beacons.com")
      .fullName("Beacons R Us")
      .build();
    given(accountHolderGateway.getById(accountHolderId))
      .willReturn(accountHolder);

    deleteBeaconService.delete(request);

    verify(noteGateway).create(noteCaptor.capture());
    final var note = noteCaptor.getValue();
    assertThat(note.getBeaconId(), is(beaconId));
    assertThat(note.getEmail(), is(nullValue()));
    assertThat(note.getFullName(), is("Account Holder"));
    assertThat(note.getUserId(), is(accountHolderId));
    assertThat(note.getType(), is(NoteType.RECORD_HISTORY));
    assertThat(
      note.getText(),
      is(
        "The account holder deleted the record with reason: '" +
        "Unused on my boat anymore" +
        "'"
      )
    );
  }
}
