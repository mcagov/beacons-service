package uk.gov.mca.beacons.api.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LegacyBeacon {

  private UUID id;
  private Map<String, Object> beacon;
  private Map<String, Object> emergencyContact;
  private List<Map<String, Object>> uses;
  private List<Map<String, Object>> owners;
}
