package uk.gov.mca.beacons.api.legacybeacon.domain;

import java.io.Serializable;
import lombok.*;
import uk.gov.mca.beacons.api.shared.domain.base.ValueObject;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegacyEmergencyContact implements ValueObject, Serializable {

  private String details;
}
