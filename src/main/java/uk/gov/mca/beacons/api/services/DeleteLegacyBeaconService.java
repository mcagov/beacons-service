package uk.gov.mca.beacons.api.services;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    deleteLegacyBeaconById(legacyBeaconId);
  }
}
