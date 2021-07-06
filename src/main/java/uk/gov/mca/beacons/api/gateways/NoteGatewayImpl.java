package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.dto.CreateNoteRequest;
import uk.gov.mca.beacons.api.jpa.NoteJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Note;
import uk.gov.mca.beacons.api.mappers.NoteMapper;

public class NoteGatewayImpl implements NoteGateway {

  private NoteJpaRepository noteJpaRepository;

  public NoteGatewayImpl(NoteJpaRepository noteRepository) {
    this.noteJpaRepository = noteRepository;
  }

  @Override
  public void save(CreateNoteRequest request) {
    final Note note = NoteMapper.toNote(request);
    this.noteJpaRepository.save(note);
  }
}
