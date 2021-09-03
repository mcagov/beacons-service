package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.gateways.NoteGateway;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

  @Mock
  private NoteGateway mockNoteGateway;

  @Nested
  class Create {

    @Test
    void shouldCreateTheNote() {
      final NoteService noteService = new NoteService(mockNoteGateway);
      final Note note = new Note();
      final Note createdNote = Note
        .builder()
        .createdDate(OffsetDateTime.now())
        .build();

      given(mockNoteGateway.create(note)).willReturn(createdNote);
      assertThat(noteService.create(note), is(createdNote));
    }
  }

  @Nested
  class GetByBeaconId {

    @Test
    void shouldReturnAListOfNotesForABeacon() {
      final NoteService noteService = new NoteService(mockNoteGateway);
      final UUID beaconId = UUID.randomUUID();

      final Note firstNote = Note.builder().beaconId(beaconId).build();
      final Note secondNote = Note.builder().beaconId(beaconId).build();

      final List<Note> foundNotes = List.of(firstNote, secondNote);

      given(mockNoteGateway.findAllByBeaconId(beaconId)).willReturn(foundNotes);

      assertThat(noteService.findAllByBeaconId(beaconId), is(foundNotes));
    }

    @Test
    void shouldReturnAnEmptyListIfThereAreNoNotesForABeacon() {
      final NoteService noteService = new NoteService(mockNoteGateway);
      final UUID beaconId = UUID.randomUUID();

      given(mockNoteGateway.findAllByBeaconId(beaconId))
        .willReturn(Collections.emptyList());

      assertThat(noteService.findAllByBeaconId(beaconId), is(empty()));
    }
  }
}
