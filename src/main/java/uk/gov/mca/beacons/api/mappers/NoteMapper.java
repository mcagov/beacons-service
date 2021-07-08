package uk.gov.mca.beacons.api.mappers;

import java.time.LocalDateTime;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

public class NoteMapper {

  public static NoteEntity toNoteEntity(Note note) {
    final LocalDateTime now = LocalDateTime.now();

    NoteEntity noteEntity = new NoteEntity();
    noteEntity.setId(note.getId());
    noteEntity.setBeaconId(note.getBeaconId());
    noteEntity.setText(note.getText());
    noteEntity.setType(note.getType());
    noteEntity.setCreatedDate(
      note.getCreatedDate() == null ? now : note.getCreatedDate()
    );
    noteEntity.setPersonId(note.getPersonId());
    noteEntity.setFullName(note.getFullName());
    noteEntity.setEmail(note.getEmail());

    return noteEntity;
  }

  public static Note fromNoteEntity(NoteEntity noteEntity) {
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
}
