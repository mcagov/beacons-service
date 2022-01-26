package uk.gov.mca.beacons.api.search.documents.nested;

import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.beaconuse.domain.BeaconUse;

@Getter
@Setter
public class NestedBeaconUse {

  public NestedBeaconUse() {}

  public NestedBeaconUse(BeaconUse beaconUse) {
    this.environment = beaconUse.getEnvironment().toString();
  }

  private String environment;
}
