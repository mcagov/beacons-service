package uk.gov.mca.beacons.api.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.NoteDTO.Attributes;
import uk.gov.mca.beacons.api.dto.WrapperDTO;

@ExtendWith(MockitoExtension.class)
class NoteMapperTest {

  @InjectMocks
  private NoteMapper noteMapper;

  @Mock
  private Clock clock;

  private Note note;
  private NoteDTO noteDTO;
  private Attributes DTOAttributes;

  @BeforeEach
  public void setup() {
    final UUID id = UUID.randomUUID();
    final UUID beaconId = UUID.randomUUID();
    final String text = "Beacon pancakes, making beacon pancakes";
    final NoteType type = NoteType.INCIDENT;
    final LocalDateTime createdDate = LocalDateTime.now();
    final UUID userId = UUID.randomUUID();
    final String fullName = "Jake The Dog";
    final String email = "i.love.lady@rainicorn.com";

    note =
      Note
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

    DTOAttributes =
      Attributes
        .builder()
        .beaconId(beaconId)
        .text(text)
        .type(type)
        .createdDate(createdDate)
        .userId(userId)
        .fullName(fullName)
        .email(email)
        .build();

    noteDTO = new NoteDTO();
    noteDTO.setId(id);
    noteDTO.setAttributes(DTOAttributes);
  }

  @Test
  void fromDTO_shouldSetAllTheFieldsOnTheNoteDTOFromTheNote() {
    final Note mappedNote = noteMapper.fromDTO(noteDTO);

    assertThat(mappedNote.getId(), is(noteDTO.getId()));
    assertThat(mappedNote.getBeaconId(), is(DTOAttributes.getBeaconId()));
    assertThat(mappedNote.getText(), is(DTOAttributes.getText()));
    assertThat(mappedNote.getType(), is(DTOAttributes.getType()));
    assertThat(mappedNote.getCreatedDate(), is(DTOAttributes.getCreatedDate()));
    assertThat(mappedNote.getUserId(), is(DTOAttributes.getUserId()));
    assertThat(mappedNote.getFullName(), is(DTOAttributes.getFullName()));
    assertThat(mappedNote.getEmail(), is(DTOAttributes.getEmail()));
  }

  @Test
  void toDTO_shouldInstantiateANoteFromTheNoteDTO() {
    final NoteDTO mappedDTO = noteMapper.toDTO(note);
    final Attributes mappedAttributes = mappedDTO.getAttributes();

    assertThat(mappedDTO.getId(), is(note.getId()));
    assertThat(mappedAttributes.getBeaconId(), is(note.getBeaconId()));
    assertThat(mappedAttributes.getText(), is(note.getText()));
    assertThat(mappedAttributes.getType(), is(note.getType()));
    assertThat(mappedAttributes.getCreatedDate(), is(note.getCreatedDate()));
    assertThat(mappedAttributes.getUserId(), is(note.getUserId()));
    assertThat(mappedAttributes.getFullName(), is(note.getFullName()));
    assertThat(mappedAttributes.getEmail(), is(note.getEmail()));
  }

  @Test
  void toWrapperDTO_shouldConvertANoteToAWrappedDTO() {
    final WrapperDTO<NoteDTO> wrappedNote = noteMapper.toWrapperDTO(note);

    assertNotNull(wrappedNote.getMeta());
    assertNotNull(wrappedNote.getData());
    assertNotNull(wrappedNote.getIncluded());
  }

  @Test
  void toOrderedWrapperDTO_shouldConvertAListOfNotesToAWrappedDTO() {
    final WrapperDTO<List<NoteDTO>> wrappedNotes = noteMapper.toOrderedWrapperDTO(
      List.of(note)
    );

    assertThat(wrappedNotes.getMeta().get("count"), is(1));
    assertNotNull(wrappedNotes.getData());
    assertNotNull(wrappedNotes.getIncluded());
  }

  @Test
  void toOrderedWrapperDTO_shouldOrderAListOfNotesByLatestFirst() {
    final var oldestNote = Note
      .builder()
      .createdDate(LocalDateTime.of(1990, 1, 1, 0, 0, 0))
      .build();
    final var middleNote = Note
      .builder()
      .createdDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
      .build();
    final var newestNote = Note
      .builder()
      .createdDate(LocalDateTime.now())
      .build();

    final WrapperDTO<List<NoteDTO>> wrappedNotes = noteMapper.toOrderedWrapperDTO(
      List.of(middleNote, oldestNote, newestNote)
    );

    assertThat(wrappedNotes.getMeta().get("count"), is(3));
    assertThat(
      wrappedNotes.getData().get(0).getAttributes().getCreatedDate(),
      is(newestNote.getCreatedDate())
    );
    assertThat(
      wrappedNotes.getData().get(1).getAttributes().getCreatedDate(),
      is(middleNote.getCreatedDate())
    );
    assertThat(
      wrappedNotes.getData().get(2).getAttributes().getCreatedDate(),
      is(oldestNote.getCreatedDate())
    );
    assertNotNull(wrappedNotes.getIncluded());
  }
}
