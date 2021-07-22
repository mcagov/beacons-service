package uk.gov.mca.beacons.api.mappers;

import static uk.gov.mca.beacons.api.dto.NoteDTO.Attributes;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

@Service
public class NoteMapper extends BaseMapper {

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
      .userId(note.getUserId())
      .fullName(note.getFullName())
      .email(note.getEmail())
      .build();
  }

  public Note fromNoteEntity(NoteEntity noteEntity) {
    return Note
      .builder()
      .id(noteEntity.getId())
      .beaconId(noteEntity.getBeaconId())
      .userId(noteEntity.getUserId())
      .fullName(noteEntity.getFullName())
      .email(noteEntity.getEmail())
      .text(noteEntity.getText())
      .type(noteEntity.getType())
      .createdDate(noteEntity.getCreatedDate())
      .build();
  }

  public Note fromDTO(NoteDTO noteDTO) {
    Attributes attributes = noteDTO.getAttributes();

    return Note
      .builder()
      .id(noteDTO.getId())
      .beaconId(attributes.getBeaconId())
      .text(attributes.getText())
      .type(attributes.getType())
      .createdDate(attributes.getCreatedDate())
      .userId(attributes.getUserId())
      .fullName(attributes.getFullName())
      .email(attributes.getEmail())
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
      .userId(note.getUserId())
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

  public WrapperDTO<List<NoteDTO>> toWrapperDTO(List<Note> notes) {
    final WrapperDTO<List<NoteDTO>> wrapperDTO = new WrapperDTO<>();
    final var noteDTOs = notes
      .stream()
      .map(this::toDTO)
      .collect(Collectors.toList());
    wrapperDTO.setData(noteDTOs);
    wrapperDTO.addMeta("count", noteDTOs.size());
    return wrapperDTO;
  }
}
