package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.domain.Note;

public interface NoteGateway {
  void create(Note note);
}
