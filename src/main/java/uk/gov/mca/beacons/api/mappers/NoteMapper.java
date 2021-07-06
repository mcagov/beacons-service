package uk.gov.mca.beacons.api.mappers;

import uk.gov.mca.beacons.api.dto.CreateNoteRequest;
import uk.gov.mca.beacons.api.jpa.entities.Note;

public class NoteMapper {

  public static Note toNote(CreateNoteRequest request) {
    Note note = new Note();
    note.setBeaconId(request.getBeaconId());
    note.setNote(request.getNote());
    note.setType(request.getType());
    note.setCreatedDate(request.getCreatedDate());
    note.setPersonId(request.getPersonId());
    note.setFullName(request.getFullName());
    note.setEmail(request.getEmail());

    return note;
  }
}
