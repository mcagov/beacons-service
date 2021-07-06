package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.jpa.NoteJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

@ExtendWith(MockitoExtension.class)
class NoteGatewayImplTest {

  @InjectMocks
  private NoteGatewayImpl noteGateway;

  @Mock
  private NoteJpaRepository noteRepository;

  @Captor
  private ArgumentCaptor<NoteEntity> noteCaptor;

  @Test
  void shouldCreateANoteFromARequestObject() {
    final UUID beaconId = UUID.randomUUID();
    final String note = "This beacon belongs to a cat.";
    final NoteType type = NoteType.GENERAL;
    final LocalDateTime createdDate = LocalDateTime.now();
    final UUID personId = UUID.randomUUID();
    final String fullName = "Alfred the cat";
    final String email = "alfred@cute.cat.com";

    final Note newNote = Note
      .builder()
      .beaconId(beaconId)
      .note(note)
      .type(type)
      .createdDate(createdDate)
      .personId(personId)
      .fullName(fullName)
      .email(email)
      .build();

    noteGateway.save(newNote);

    verify(noteRepository).save(noteCaptor.capture());
    final NoteEntity createdNote = noteCaptor.getValue();

    assertThat(createdNote.getBeaconId(), is(beaconId));
    assertThat(createdNote.getNote(), is(note));
    assertThat(createdNote.getType(), is(type));
    assertThat(createdNote.getCreatedDate(), is(createdDate));
    assertThat(createdNote.getPersonId(), is(personId));
    assertThat(createdNote.getFullName(), is(fullName));
    assertThat(createdNote.getEmail(), is(email));
  }
}
