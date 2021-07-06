package uk.gov.mca.beacons.api.mappers;

import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

public class NoteMapper {

  public static NoteEntity toNoteEntity(Note note) {
    NoteEntity noteEntity = new NoteEntity();
    noteEntity.setBeaconId(note.getBeaconId());
    noteEntity.setText(note.getText());
    noteEntity.setType(note.getType());
    noteEntity.setCreatedDate(note.getCreatedDate());
    noteEntity.setPersonId(note.getPersonId());
    noteEntity.setFullName(note.getFullName());
    noteEntity.setEmail(note.getEmail());

    return noteEntity;
  }
}
