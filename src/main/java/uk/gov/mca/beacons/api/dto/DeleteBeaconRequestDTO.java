package uk.gov.mca.beacons.api.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteBeaconRequestDTO {

  private UUID beaconId;
  private UUID accountHolderId;
  private String reason;
}
