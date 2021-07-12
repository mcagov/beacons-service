package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
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

  @Test
  void shouldCreateTheNote() {
    final NoteService noteService = new NoteService(mockNoteGateway);
    final Note note = new Note();
    final Note createdNote = Note
      .builder()
      .createdDate(LocalDateTime.now())
      .build();

    given(mockNoteGateway.create(note)).willReturn(createdNote);
    assertThat(noteService.create(note), is(createdNote));
  }
}
