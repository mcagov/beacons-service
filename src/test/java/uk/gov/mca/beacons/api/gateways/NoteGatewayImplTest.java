package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.jpa.NoteJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;
import uk.gov.mca.beacons.api.mappers.NoteMapper;

@ExtendWith(MockitoExtension.class)
class NoteGatewayImplTest {

  @Mock
  private NoteJpaRepository noteRepository;

  private Clock fixedClock = Clock.fixed(
    Instant.parse("2021-01-01T00:00:00Z"),
    ZoneId.of("UTC")
  );

  @Captor
  private ArgumentCaptor<NoteEntity> noteCaptor;

  @Test
  void shouldCreateANoteEntityFromANote() {
    final UUID id = UUID.randomUUID();
    final UUID beaconId = UUID.randomUUID();
    final String text = "This beacon belongs to a cat.";
    final NoteType type = NoteType.GENERAL;
    final LocalDateTime createdDate = LocalDateTime.now();
    final UUID personId = UUID.randomUUID();
    final String fullName = "Alfred the cat";
    final String email = "alfred@cute.cat.com";

    final User user = User
      .builder()
      .authId(personId)
      .fullName(fullName)
      .email(email)
      .build();

    final Note note = Note
      .builder()
      .beaconId(beaconId)
      .text(text)
      .type(type)
      .createdDate(createdDate)
      .user(user)
      .build();

    final NoteEntity createdEntity = NoteEntity
      .builder()
      .id(id)
      .beaconId(beaconId)
      .text(text)
      .type(type)
      .createdDate(createdDate)
      .personId(personId)
      .fullName(fullName)
      .email(email)
      .build();

    final NoteMapper noteMapper = new NoteMapper(fixedClock);
    final NoteGateway noteGateway = new NoteGatewayImpl(
      noteMapper,
      noteRepository
    );

    when(noteRepository.save(any(NoteEntity.class))).thenReturn(createdEntity);

    final Note createdNote = noteGateway.create(note);
    final User noteUser = createdNote.getUser();

    assertThat(createdNote.getId(), is(id));
    assertThat(createdNote.getBeaconId(), is(beaconId));
    assertThat(createdNote.getText(), is(text));
    assertThat(createdNote.getType(), is(type));
    assertThat(createdNote.getCreatedDate(), is(createdDate));
    assertThat(noteUser.getAuthId(), is(personId));
    assertThat(noteUser.getFullName(), is(fullName));
    assertThat(noteUser.getEmail(), is(email));
  }

  @Test
  void shouldSetCreatedDateIfNotProvided() {
    final UUID personId = UUID.randomUUID();
    final String fullName = "Loki the cat";
    final String email = "loki@cute.cat.com";

    final User user = User
      .builder()
      .authId(personId)
      .fullName(fullName)
      .email(email)
      .build();

    final Note note = Note.builder().user(user).build();
    final NoteEntity createdEntity = new NoteEntity();

    final NoteMapper noteMapper = new NoteMapper(fixedClock);
    final NoteGateway noteGateway = new NoteGatewayImpl(
      noteMapper,
      noteRepository
    );

    when(noteRepository.save(noteCaptor.capture())).thenReturn(createdEntity);

    noteGateway.create(note);

    final NoteEntity entity = noteCaptor.getValue();
    assertThat(
      entity.getCreatedDate(),
      is(equalTo(LocalDateTime.now(fixedClock)))
    );
  }
}
