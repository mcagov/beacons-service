package uk.gov.mca.beacons.api.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
