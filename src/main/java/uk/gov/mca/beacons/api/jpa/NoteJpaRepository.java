package uk.gov.mca.beacons.api.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.gov.mca.beacons.api.jpa.entities.Note;

public interface NoteJpaRepository extends JpaRepository<Note, UUID> {
  @Query(
    nativeQuery = true,
    value = "SELECT * FROM beacon_note WHERE beacon_id = ?1"
  )
  List<Note> findAllByBeaconId(UUID beaconId);
}
