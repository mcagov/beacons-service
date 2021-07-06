package uk.gov.mca.beacons.api.gateways;

import uk.gov.mca.beacons.api.dto.CreateNoteRequest;

public interface NoteGateway {
  void save(CreateNoteRequest request);
}
