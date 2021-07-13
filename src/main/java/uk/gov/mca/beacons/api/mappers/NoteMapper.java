package uk.gov.mca.beacons.api.mappers;

import static uk.gov.mca.beacons.api.dto.NoteDTO.Attributes;

import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

@Service
public class NoteMapper {

  private Clock clock;

  @Autowired
  public NoteMapper(Clock clock) {
    this.clock = clock;
  }

  public NoteEntity toNoteEntity(Note note) {
    final LocalDateTime now = LocalDateTime.now(clock);

    return NoteEntity
      .builder()
      .id(note.getId())
      .beaconId(note.getBeaconId())
      .text(note.getText())
      .type(note.getType())
      .createdDate(note.getCreatedDate() == null ? now : note.getCreatedDate())
      .personId(note.getPersonId())
      .fullName(note.getFullName())
      .email(note.getEmail())
      .build();
  }

  public Note fromNoteEntity(NoteEntity noteEntity) {
    return Note
      .builder()
      .id(noteEntity.getId())
      .beaconId(noteEntity.getBeaconId())
      .text(noteEntity.getText())
      .type(noteEntity.getType())
      .createdDate(noteEntity.getCreatedDate())
      .personId(noteEntity.getPersonId())
      .fullName(noteEntity.getFullName())
      .email(noteEntity.getEmail())
      .build();
  }

  public Note fromDTO(NoteDTO noteDTO) {
    return Note
      .builder()
      .id(noteDTO.getId())
      .beaconId(noteDTO.getAttributes().getBeaconId())
      .text(noteDTO.getAttributes().getText())
      .type(noteDTO.getAttributes().getType())
      .createdDate(noteDTO.getAttributes().getCreatedDate())
      .personId(noteDTO.getAttributes().getPersonId())
      .fullName(noteDTO.getAttributes().getFullName())
      .email(noteDTO.getAttributes().getEmail())
      .build();
  }

  public NoteDTO toDTO(Note note) {
    final NoteDTO noteDTO = new NoteDTO();
    noteDTO.setId(note.getId());

    final Attributes attributes = Attributes
      .builder()
      .beaconId(note.getBeaconId())
      .text(note.getText())
      .type(note.getType())
      .createdDate(note.getCreatedDate())
      .personId(note.getPersonId())
      .fullName(note.getFullName())
      .email(note.getEmail())
      .build();

    noteDTO.setAttributes(attributes);
    return noteDTO;
  }

  public WrapperDTO<NoteDTO> toWrapperDTO(Note note) {
    final WrapperDTO<NoteDTO> wrapperDTO = new WrapperDTO<>();
    wrapperDTO.setData(toDTO(note));
    return wrapperDTO;
  }
}
