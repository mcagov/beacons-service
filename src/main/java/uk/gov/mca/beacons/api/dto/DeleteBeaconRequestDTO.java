package uk.gov.mca.beacons.api.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;
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
  private UUID actorId;

  @NotNull(message = "Reason for deleting a beacon must be defined")
  private String reason;
}
