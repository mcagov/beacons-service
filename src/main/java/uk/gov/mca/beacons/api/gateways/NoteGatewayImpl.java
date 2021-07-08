package uk.gov.mca.beacons.api.gateways;

import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.jpa.NoteJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;
import uk.gov.mca.beacons.api.mappers.NoteMapper;

@Repository
@Transactional
public class NoteGatewayImpl implements NoteGateway {

  private NoteJpaRepository noteJpaRepository;

  public NoteGatewayImpl(NoteJpaRepository noteRepository) {
    this.noteJpaRepository = noteRepository;
  }

  @Override
  public Note create(Note note) {
    final NoteEntity noteEntity = NoteMapper.toNoteEntity(note);
    final NoteEntity createdEntity = noteJpaRepository.save(noteEntity);
    return NoteMapper.fromNoteEntity(createdEntity);
  }
}
