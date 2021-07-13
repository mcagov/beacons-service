package uk.gov.mca.beacons.api.gateways;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.jpa.NoteJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;
import uk.gov.mca.beacons.api.mappers.NoteMapper;

@Repository
@Transactional
public class NoteGatewayImpl implements NoteGateway {

  private final NoteMapper noteMapper;
  private final NoteJpaRepository noteJpaRepository;

  @Autowired
  public NoteGatewayImpl(
    NoteMapper noteMapper,
    NoteJpaRepository noteRepository
  ) {
    this.noteMapper = noteMapper;
    this.noteJpaRepository = noteRepository;
  }

  @Override
  public Note create(Note note) {
    final NoteEntity noteEntity = noteMapper.toNoteEntity(note);
    final NoteEntity createdEntity = noteJpaRepository.save(noteEntity);
    return NoteMapper.fromNoteEntity(createdEntity);
  }
}
