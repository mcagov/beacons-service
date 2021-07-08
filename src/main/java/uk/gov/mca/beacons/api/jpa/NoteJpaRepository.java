package uk.gov.mca.beacons.api.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

public interface NoteJpaRepository extends JpaRepository<NoteEntity, UUID> {
  @Query(nativeQuery = true, value = "SELECT * FROM note WHERE beacon_id = ?1")
  List<NoteEntity> findAllByBeaconId(UUID beaconId);
}
