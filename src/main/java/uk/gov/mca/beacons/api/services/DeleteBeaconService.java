package uk.gov.mca.beacons.api.services;

import static java.time.LocalDateTime.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;
import uk.gov.mca.beacons.api.exceptions.AccountHolderNotFoundException;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;
import uk.gov.mca.beacons.api.gateways.NoteGateway;

@Service
@Transactional
public class DeleteBeaconService {

  private final BeaconGateway beaconGateway;
  private final AccountHolderGateway accountHolderGateway;
  private final NoteGateway noteGateway;

  @Autowired
  public DeleteBeaconService(
    BeaconGateway beaconGateway,
    AccountHolderGateway accountHolderGateway,
    NoteGateway noteGateway
  ) {
    this.beaconGateway = beaconGateway;
    this.accountHolderGateway = accountHolderGateway;
    this.noteGateway = noteGateway;
  }

  public void delete(DeleteBeaconRequestDTO request) {
    final AccountHolder accountHolder = accountHolderGateway.getById(
      request.getAccountHolderId()
    );
    if (accountHolder == null) throw new AccountHolderNotFoundException();

    final Note note = Note
      .builder()
      .beaconId(request.getBeaconId())
      .email(accountHolder.getEmail())
      .fullName(accountHolder.getFullName())
      .personId(request.getAccountHolderId())
      .type(NoteType.RECORD_HISTORY)
      .text(request.getReason())
      .createdDate(now())
      .build();
    noteGateway.create(note);

    beaconGateway.delete(request.getBeaconId());
  }
}
