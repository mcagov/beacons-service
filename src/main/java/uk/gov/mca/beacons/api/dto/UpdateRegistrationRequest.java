package uk.gov.mca.beacons.api.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@Getter
@AllArgsConstructor
public class UpdateRegistrationRequest {

  private final UUID beaconId;
  private final Beacon beacon;
}
