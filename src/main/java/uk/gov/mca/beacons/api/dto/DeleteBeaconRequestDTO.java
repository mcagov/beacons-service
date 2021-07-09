package uk.gov.mca.beacons.api.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBeaconRequestDTO {

  private UUID beaconId;
  private UUID accountHolderId;
  private String reason;
}
