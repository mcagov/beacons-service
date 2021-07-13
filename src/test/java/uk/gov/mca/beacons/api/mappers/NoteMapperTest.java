package uk.gov.mca.beacons.api.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.NoteDTO.Attributes;
import uk.gov.mca.beacons.api.dto.WrapperDTO;

@TestInstance(Lifecycle.PER_CLASS)
class NoteMapperTest {

  private Note note;
  private NoteDTO noteDTO;
  private Attributes DTOAttributes;

  @BeforeAll
  public void setup() {
    final UUID id = UUID.randomUUID();
    final UUID beaconId = UUID.randomUUID();
    final String text = "Beacon pancakes, making beacon pancakes";
    final NoteType type = NoteType.INCIDENT;
    final LocalDateTime createdDate = LocalDateTime.now();
    final UUID personId = UUID.randomUUID();
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
        .personId(personId)
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
        .personId(personId)
        .fullName(fullName)
        .email(email)
        .build();

    noteDTO = new NoteDTO();
    noteDTO.setId(id);
    noteDTO.setAttributes(DTOAttributes);
  }

  @Test
  void fromDTO_shouldSetAllTheFieldsOnTheNoteDTOFromTheNote() {
    final Note mappedNote = NoteMapper.fromDTO(noteDTO);

    assertThat(mappedNote.getId(), is(noteDTO.getId()));
    assertThat(mappedNote.getBeaconId(), is(DTOAttributes.getBeaconId()));
    assertThat(mappedNote.getText(), is(DTOAttributes.getText()));
    assertThat(mappedNote.getType(), is(DTOAttributes.getType()));
    assertThat(mappedNote.getCreatedDate(), is(DTOAttributes.getCreatedDate()));
    assertThat(mappedNote.getPersonId(), is(DTOAttributes.getPersonId()));
    assertThat(mappedNote.getFullName(), is(DTOAttributes.getFullName()));
    assertThat(mappedNote.getEmail(), is(DTOAttributes.getEmail()));
  }

  @Test
  void toDTO_shouldInstantiateANoteFromTheNoteDTO() {
    final NoteDTO mappedDTO = NoteMapper.toDTO(note);
    final Attributes mappedAttributes = mappedDTO.getAttributes();

    assertThat(mappedDTO.getId(), is(note.getId()));
    assertThat(mappedAttributes.getBeaconId(), is(note.getBeaconId()));
    assertThat(mappedAttributes.getText(), is(note.getText()));
    assertThat(mappedAttributes.getType(), is(note.getType()));
    assertThat(mappedAttributes.getCreatedDate(), is(note.getCreatedDate()));
    assertThat(mappedAttributes.getPersonId(), is(note.getPersonId()));
    assertThat(mappedAttributes.getFullName(), is(note.getFullName()));
    assertThat(mappedAttributes.getEmail(), is(note.getEmail()));
  }

  @Test
  void toWrapperDTO_shouldConvertANoteToAWrappedDTO() {
    final WrapperDTO<NoteDTO> wrappedNote = NoteMapper.toWrapperDTO(note);

    assertNotNull(wrappedNote.getMeta());
    assertNotNull(wrappedNote.getData());
    assertNotNull(wrappedNote.getIncluded());
  }
}
