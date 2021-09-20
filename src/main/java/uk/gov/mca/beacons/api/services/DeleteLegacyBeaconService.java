package uk.gov.mca.beacons.api.services;

import static java.lang.String.format;
import static java.time.OffsetDateTime.now;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.dto.DeleteLegacyBeaconRequestDTO;
import uk.gov.mca.beacons.api.exceptions.UserNotFoundException;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.gateways.NoteGateway;
import uk.gov.mca.beacons.api.gateways.UserGateway;

@Service
@Transactional
public class DeleteLegacyBeaconService {

  private static final String TEMPLATE_REASON_TEXT =
    "The account holder deleted the record with reason: '%s'";

  private final LegacyBeaconGateway legacyBeaconGateway;
  private final UserGateway userGateway;
  private final NoteGateway noteGateway;

  @Autowired
  public DeleteLegacyBeaconService(
    LegacyBeaconGateway legacyBeaconGateway,
    UserGateway userGateway,
    NoteGateway noteGateway
  ) {
    this.legacyBeaconGateway = legacyBeaconGateway;
    this.userGateway = userGateway;
    this.noteGateway = noteGateway;
  }

  private void deleteLegacyBeaconById(UUID legacyBeaconId) {
    legacyBeaconGateway.delete(legacyBeaconId);
  }

  public void delete(DeleteLegacyBeaconRequestDTO request) {
    final User user = userGateway.getUserById(request.getUserId());
    if (user == null) {
      throw new UserNotFoundException();
    }

    final var legacyBeaconId = request.getLegacyBeaconId();

    final Note note = Note
      .builder()
      .legacyBeaconId(legacyBeaconId)
      .userId(request.getUserId())
      .type(NoteType.RECORD_HISTORY)
      .text(format(TEMPLATE_REASON_TEXT, request.getReason()))
      .createdDate(now())
      .build();
    noteGateway.create(note);

    deleteLegacyBeaconById(legacyBeaconId);
  }
}
