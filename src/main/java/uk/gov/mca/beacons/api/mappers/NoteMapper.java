package uk.gov.mca.beacons.api.mappers;

import static uk.gov.mca.beacons.api.dto.NoteDTO.Attributes;

import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.User;
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
    final User user = note.getUser();

    return NoteEntity
      .builder()
      .id(note.getId())
      .beaconId(note.getBeaconId())
      .text(note.getText())
      .type(note.getType())
      .createdDate(note.getCreatedDate() == null ? now : note.getCreatedDate())
      .personId(user.getAuthId())
      .fullName(user.getFullName())
      .email(user.getEmail())
      .build();
  }

  public Note fromNoteEntity(NoteEntity noteEntity) {
    User user = User
      .builder()
      .authId(noteEntity.getPersonId())
      .fullName(noteEntity.getFullName())
      .email(noteEntity.getEmail())
      .build();

    Note note = Note
      .builder()
      .id(noteEntity.getId())
      .beaconId(noteEntity.getBeaconId())
      .text(noteEntity.getText())
      .type(noteEntity.getType())
      .createdDate(noteEntity.getCreatedDate())
      .build();

    note.setUser(user);

    return note;
  }

  public Note fromDTO(NoteDTO noteDTO) {
    Attributes attributes = noteDTO.getAttributes();
    User user = getUserFromAttributes(attributes);

    return Note
      .builder()
      .id(noteDTO.getId())
      .beaconId(attributes.getBeaconId())
      .text(attributes.getText())
      .type(attributes.getType())
      .createdDate(attributes.getCreatedDate())
      .user(user)
      .build();
  }

  public NoteDTO toDTO(Note note) {
    final NoteDTO noteDTO = new NoteDTO();
    noteDTO.setId(note.getId());

    final User user = note.getUser();

    final Attributes attributes = Attributes
      .builder()
      .beaconId(note.getBeaconId())
      .text(note.getText())
      .type(note.getType())
      .createdDate(note.getCreatedDate())
      .personId(user.getAuthId())
      .fullName(user.getFullName())
      .email(user.getEmail())
      .build();

    noteDTO.setAttributes(attributes);
    return noteDTO;
  }

  public WrapperDTO<NoteDTO> toWrapperDTO(Note note) {
    final WrapperDTO<NoteDTO> wrapperDTO = new WrapperDTO<>();
    wrapperDTO.setData(toDTO(note));
    return wrapperDTO;
  }

  private User getUserFromAttributes(Attributes attributes) {
    if (
      attributes.getPersonId() == null &&
      attributes.getFullName() == null &&
      attributes.getEmail() == null
    ) return null;

    return User
      .builder()
      .authId(attributes.getPersonId())
      .fullName(attributes.getFullName())
      .email(attributes.getEmail())
      .build();
  }
}
