package uk.gov.mca.beacons.api.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AccountHolderIdDTO {

  @Getter
  private final UUID id;
}
