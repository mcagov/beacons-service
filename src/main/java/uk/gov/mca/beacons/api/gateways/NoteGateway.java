package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import uk.gov.mca.beacons.api.domain.Note;

public interface NoteGateway {
  Note create(Note note);

  List<Note> findAllByBeaconId(UUID beaconId);

  List<Note> findAllByLegacyBeaconId(UUID legacyBeaconId);
}
