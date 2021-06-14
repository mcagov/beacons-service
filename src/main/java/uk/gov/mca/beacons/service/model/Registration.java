package uk.gov.mca.beacons.service.model;

import java.util.List;
import javax.validation.Valid;

public class Registration {

  @Valid
  private List<Beacon> beacons;

  public List<Beacon> getBeacon() {
    return beacons;
  }

  public void setBeacons(List<Beacon> beacons) {
    this.beacons = beacons;
  }
}
