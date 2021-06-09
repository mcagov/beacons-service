package uk.gov.mca.beacons.service.dto;

import java.util.UUID;

public class AccountHolderIdDTO {

  private final UUID id;

  public AccountHolderIdDTO(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }
}
