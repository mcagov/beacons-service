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
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;
import uk.gov.mca.beacons.api.exceptions.UserNotFoundException;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;
import uk.gov.mca.beacons.api.gateways.EmergencyContactGateway;
import uk.gov.mca.beacons.api.gateways.NoteGateway;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.gateways.UseGateway;

@Service
@Transactional
public class DeleteBeaconService {

  private static final String TEMPLATE_REASON_TEXT =
    "The account holder deleted the record with reason: '%s'";

  private final BeaconGateway beaconGateway;
  private final AccountHolderGateway accountHolderGateway;
  private final NoteGateway noteGateway;
  private final OwnerGateway ownerGateway;
  private final EmergencyContactGateway emergencyContactGateway;
  private final UseGateway useGateway;

  @Autowired
  public DeleteBeaconService(
    BeaconGateway beaconGateway,
    AccountHolderGateway accountHolderGateway,
    NoteGateway noteGateway,
    OwnerGateway ownerGateway,
    EmergencyContactGateway emergencyContactGateway,
    UseGateway useGateway
  ) {
    this.beaconGateway = beaconGateway;
    this.accountHolderGateway = accountHolderGateway;
    this.noteGateway = noteGateway;
    this.ownerGateway = ownerGateway;
    this.emergencyContactGateway = emergencyContactGateway;
    this.useGateway = useGateway;
  }

  public void deleteOwnerByBeaconId(UUID beaconIdToBeDeleted) {
    ownerGateway.deleteByBeaconId(beaconIdToBeDeleted);
  }

  public void deleteEmergencyContactByBeaconId(UUID beaconIdToBeDeleted) {
    emergencyContactGateway.deleteAllByBeaconId(beaconIdToBeDeleted);
  }

  public void deleteUseByBeaconId(UUID beaconIdToBeDeleted) {
    useGateway.deleteAllByBeaconId(beaconIdToBeDeleted);
  }

  public void deleteBeaconByBeaconId(UUID beaconIdToBeDeleted) {
    beaconGateway.delete(beaconIdToBeDeleted);
  }

  public void delete(DeleteBeaconRequestDTO request) {
    final User user = accountHolderGateway.getById(request.getUserId());
    if (user == null) throw new UserNotFoundException();

    final var beaconIdToBeDeleted = request.getBeaconId();

    final Note note = Note
      .builder()
      .beaconId(beaconIdToBeDeleted)
      .userId(request.getUserId())
      .fullName("Account Holder")
      .type(NoteType.RECORD_HISTORY)
      .text(format(TEMPLATE_REASON_TEXT, request.getReason()))
      .createdDate(now())
      .build();
    noteGateway.create(note);

    deleteOwnerByBeaconId(beaconIdToBeDeleted);

    deleteEmergencyContactByBeaconId(beaconIdToBeDeleted);

    deleteUseByBeaconId(beaconIdToBeDeleted);

    deleteBeaconByBeaconId(beaconIdToBeDeleted);
  }
}
