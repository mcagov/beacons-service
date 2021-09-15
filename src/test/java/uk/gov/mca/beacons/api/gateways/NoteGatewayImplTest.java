package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
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

  @Nested
  class CreateNote {

    @Test
    void shouldCreateANoteEntityFromANoteWithABeaconId() {
      final UUID id = UUID.randomUUID();
      final UUID beaconId = UUID.randomUUID();
      final String text = "This beacon belongs to a cat.";
      final NoteType type = NoteType.GENERAL;
      final OffsetDateTime createdDate = OffsetDateTime.now();
      final UUID userId = UUID.randomUUID();
      final String fullName = "Alfred the cat";
      final String email = "alfred@cute.cat.com";

      final Note note = Note
        .builder()
        .beaconId(beaconId)
        .text(text)
        .type(type)
        .createdDate(createdDate)
        .userId(userId)
        .fullName(fullName)
        .email(email)
        .build();

      final NoteEntity createdEntity = NoteEntity
        .builder()
        .id(id)
        .beaconId(beaconId)
        .text(text)
        .type(type)
        .createdDate(createdDate)
        .userId(userId)
        .fullName(fullName)
        .email(email)
        .build();

      final NoteMapper noteMapper = new NoteMapper(fixedClock);
      final NoteGateway noteGateway = new NoteGatewayImpl(
        noteMapper,
        noteRepository
      );

      when(noteRepository.save(any(NoteEntity.class)))
        .thenReturn(createdEntity);

      final Note createdNote = noteGateway.create(note);

      assertThat(createdNote.getId(), is(id));
      assertThat(createdNote.getBeaconId(), is(beaconId));
      assertThat(createdNote.getText(), is(text));
      assertThat(createdNote.getType(), is(type));
      assertThat(createdNote.getCreatedDate(), is(createdDate));
      assertThat(createdNote.getUserId(), is(userId));
      assertThat(createdNote.getFullName(), is(fullName));
      assertThat(createdNote.getEmail(), is(email));
    }

    @Test
    void shouldCreateANoteEntityFromANoteWithALegacyBeaconId() {
      final UUID id = UUID.randomUUID();
      final UUID legacyBeaconId = UUID.randomUUID();
      final String text = "This beacon belongs to a cat.";
      final NoteType type = NoteType.GENERAL;
      final OffsetDateTime createdDate = OffsetDateTime.now();
      final UUID userId = UUID.randomUUID();
      final String fullName = "Alfred the cat";
      final String email = "alfred@cute.cat.com";

      final Note note = Note
        .builder()
        .legacyBeaconId(legacyBeaconId)
        .text(text)
        .type(type)
        .createdDate(createdDate)
        .userId(userId)
        .fullName(fullName)
        .email(email)
        .build();

      final NoteEntity createdEntity = NoteEntity
        .builder()
        .id(id)
        .legacyBeaconId(legacyBeaconId)
        .text(text)
        .type(type)
        .createdDate(createdDate)
        .userId(userId)
        .fullName(fullName)
        .email(email)
        .build();

      final NoteMapper noteMapper = new NoteMapper(fixedClock);
      final NoteGateway noteGateway = new NoteGatewayImpl(
        noteMapper,
        noteRepository
      );

      when(noteRepository.save(any(NoteEntity.class)))
        .thenReturn(createdEntity);

      final Note createdNote = noteGateway.create(note);

      assertThat(createdNote.getId(), is(id));
      assertThat(createdNote.getLegacyBeaconId(), is(legacyBeaconId));
      assertThat(createdNote.getText(), is(text));
      assertThat(createdNote.getType(), is(type));
      assertThat(createdNote.getCreatedDate(), is(createdDate));
      assertThat(createdNote.getUserId(), is(userId));
      assertThat(createdNote.getFullName(), is(fullName));
      assertThat(createdNote.getEmail(), is(email));
    }

    @Test
    void shouldSetCreatedDateIfNotProvided() {
      final Note note = new Note();
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
        is(equalTo(OffsetDateTime.now(fixedClock)))
      );
    }
  }

  @Nested
  class GetNotesByBeaconId {

    @Test
    void shouldReturnNotesRelatedToABeacons() {
      final UUID beaconId = UUID.randomUUID();
      final NoteEntity firstNoteEntity = NoteEntity
        .builder()
        .beaconId(beaconId)
        .build();
      final NoteEntity secondNoteEntity = NoteEntity
        .builder()
        .beaconId(beaconId)
        .build();

      final List<NoteEntity> foundNoteEntities = new ArrayList<>();
      foundNoteEntities.add(firstNoteEntity);
      foundNoteEntities.add(secondNoteEntity);

      when(noteRepository.findAllByBeaconId(beaconId))
        .thenReturn(foundNoteEntities);

      final NoteMapper noteMapper = new NoteMapper(fixedClock);
      final NoteGateway noteGateway = new NoteGatewayImpl(
        noteMapper,
        noteRepository
      );

      List<Note> foundNotes = noteGateway.findAllByBeaconId(beaconId);

      foundNotes.forEach(note -> assertThat(note.getBeaconId(), is(beaconId)));
    }

    @Test
    void shouldReturnAnEmptyListIfNoNotesAreFound() {
      final UUID beaconId = UUID.randomUUID();

      when(noteRepository.findAllByBeaconId(beaconId))
        .thenReturn(Collections.emptyList());

      final NoteMapper noteMapper = new NoteMapper(fixedClock);
      final NoteGateway noteGateway = new NoteGatewayImpl(
        noteMapper,
        noteRepository
      );

      assertThat(noteGateway.findAllByBeaconId(beaconId), is(empty()));
    }
  }

  @Nested
  class GetNotesByLegacyBeaconId {

    @Test
    void shouldReturnNotesRelatedToALegacyBeacon() {
      final UUID legacyBeaconId = UUID.randomUUID();
      final NoteEntity firstNoteEntity = NoteEntity
        .builder()
        .legacyBeaconId(legacyBeaconId)
        .build();
      final NoteEntity secondNoteEntity = NoteEntity
        .builder()
        .legacyBeaconId(legacyBeaconId)
        .build();

      final List<NoteEntity> foundNoteEntities = new ArrayList<>();
      foundNoteEntities.add(firstNoteEntity);
      foundNoteEntities.add(secondNoteEntity);

      when(noteRepository.findAllByLegacyBeaconId(legacyBeaconId))
        .thenReturn(foundNoteEntities);

      final NoteMapper noteMapper = new NoteMapper(fixedClock);
      final NoteGateway noteGateway = new NoteGatewayImpl(
        noteMapper,
        noteRepository
      );

      List<Note> foundNotes = noteGateway.findAllByLegacyBeaconId(
        legacyBeaconId
      );

      foundNotes.forEach(
        note -> assertThat(note.getLegacyBeaconId(), is(legacyBeaconId))
      );
    }

    @Test
    void shouldReturnAnEmptyListIfNoNotesAreFound() {
      final UUID legacyBeaconId = UUID.randomUUID();

      when(noteRepository.findAllByLegacyBeaconId(legacyBeaconId))
        .thenReturn(Collections.emptyList());

      final NoteMapper noteMapper = new NoteMapper(fixedClock);
      final NoteGateway noteGateway = new NoteGatewayImpl(
        noteMapper,
        noteRepository
      );

      assertThat(
        noteGateway.findAllByLegacyBeaconId(legacyBeaconId),
        is(empty())
      );
    }
  }
}
