package uk.gov.mca.beacons.api.services;

import static java.lang.String.format;
import static java.time.OffsetDateTime.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;
import uk.gov.mca.beacons.api.exceptions.UserNotFoundException;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;
import uk.gov.mca.beacons.api.gateways.NoteGateway;
import uk.gov.mca.beacons.api.gateways.UserGateway;

@Service
@Transactional
public class DeleteBeaconService {

  private static final String TEMPLATE_REASON_TEXT =
    "The account holder deleted the record with reason: '%s'";

  private final BeaconGateway beaconGateway;
  private final UserGateway userGateway;
  private final NoteGateway noteGateway;

  @Autowired
  public DeleteBeaconService(
    BeaconGateway beaconGateway,
    UserGateway userGateway,
    NoteGateway noteGateway
  ) {
    this.beaconGateway = beaconGateway;
    this.userGateway = userGateway;
    this.noteGateway = noteGateway;
  }

  public void delete(DeleteBeaconRequestDTO request) {
    final User user = userGateway.getUserById(request.getUserId());
    if (user == null) throw new UserNotFoundException();

    final Note note = Note
      .builder()
      .beaconId(request.getBeaconId())
      .email(user.getEmail())
      .fullName(user.getFullName())
      .userId(request.getUserId())
      .type(NoteType.RECORD_HISTORY)
      .text(format(TEMPLATE_REASON_TEXT, request.getReason()))
      .createdDate(now())
      .build();
    noteGateway.create(note);

    beaconGateway.delete(request.getBeaconId());
  }
}
