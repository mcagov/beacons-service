package uk.gov.mca.beacons.api.services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.gateways.NoteGateway;

@Service
@Transactional
public class NoteService {

  private final NoteGateway noteGateway;

  @Autowired
  public NoteService(NoteGateway noteGateway) {
    this.noteGateway = noteGateway;
  }

  public Note create(Note note) {
    return noteGateway.create(note);
  }

  public List<Note> findAllByBeaconId(UUID beaconId) {
    return noteGateway.findAllByBeaconId(beaconId);
  }

  public List<Note> findAllByLegacyBeaconId(UUID legacyBeaconId) {
    return noteGateway.findAllByLegacyBeaconId(legacyBeaconId);
  }
}
