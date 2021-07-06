package uk.gov.mca.beacons.api.mappers;

import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

public class NoteMapper {

  public static NoteEntity toNoteEntity(Note note) {
    NoteEntity noteEntity = new NoteEntity();
    note.setBeaconId(note.getBeaconId());
    note.setNote(note.getNote());
    note.setType(note.getType());
    note.setCreatedDate(note.getCreatedDate());
    note.setPersonId(note.getPersonId());
    note.setFullName(note.getFullName());
    note.setEmail(note.getEmail());

    return noteEntity;
  }
}
