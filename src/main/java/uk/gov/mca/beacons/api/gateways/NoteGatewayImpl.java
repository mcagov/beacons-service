package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
    return noteMapper.fromNoteEntity(createdEntity);
  }

  @Override
  public List<Note> findAllByBeaconId(UUID beaconId) {
    List<NoteEntity> foundEntities = noteJpaRepository.findAllByBeaconId(
      beaconId
    );
    return foundEntities
      .stream()
      .map(noteEntity -> noteMapper.fromNoteEntity(noteEntity))
      .collect(Collectors.toList());
  }
}
