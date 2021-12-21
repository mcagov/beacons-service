package uk.gov.mca.beacons.api.note.application;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.beacon.domain.BeaconId;
import uk.gov.mca.beacons.api.note.domain.Note;
import uk.gov.mca.beacons.api.note.domain.NoteRepository;

@Service("NoteServiceV2")
public class NoteService {

  private final NoteRepository noteRepository;

  @Autowired
  public NoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  public Note create(Note note) {
    return noteRepository.save(note);
  }

  public List<Note> getByBeaconId(BeaconId beaconId) {
    return noteRepository.findByBeaconId(beaconId);
  }
}
