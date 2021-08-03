package uk.gov.mca.beacons.api.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegacyBeacon {

  private UUID id;
  private BeaconStatus beaconStatus;
  private Map<String, Object> beacon;
  private List<Map<String, Object>> uses;
  private Map<String, Object> owner;
  private List<Map<String, Object>> secondaryOwners;
  private Map<String, Object> emergencyContact;
}
