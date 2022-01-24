package uk.gov.mca.beacons.api.search.documents.nested;

import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwner;

@Getter
@Setter
public class NestedBeaconOwner {

  public NestedBeaconOwner() {}

  public NestedBeaconOwner(BeaconOwner beaconOwner) {
    this.ownerName = beaconOwner.getFullName();
  }

  private String ownerName;
}
