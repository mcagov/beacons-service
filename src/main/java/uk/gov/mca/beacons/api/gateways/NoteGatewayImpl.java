package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.jpa.NoteJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;
import uk.gov.mca.beacons.api.mappers.NoteMapper;

public class NoteGatewayImpl implements NoteGateway {

  private NoteJpaRepository noteJpaRepository;

  public NoteGatewayImpl(NoteJpaRepository noteRepository) {
    this.noteJpaRepository = noteRepository;
  }

  @Override
  public Note create(Note note) {
    final NoteEntity noteEntity = NoteMapper.toNoteEntity(note);
    final NoteEntity createdNote = noteJpaRepository.save(noteEntity);
    return NoteMapper.fromNoteEntity(createdNote);
  }
}
