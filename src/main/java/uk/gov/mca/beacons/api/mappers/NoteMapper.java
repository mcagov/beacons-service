package uk.gov.mca.beacons.api.mappers;

import java.time.LocalDateTime;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

public class NoteMapper {

  public static NoteEntity toNoteEntity(Note note) {
    final LocalDateTime now = LocalDateTime.now();

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
