package uk.gov.mca.beacons.service.model;

import java.util.List;

public class Registration {

  private List<Beacon> beacons;

  public List<Beacon> getBeacons() {
    return beacons;
  }

  public void setBeacons(List<Beacon> beacons) {
    this.beacons = beacons;
  }
}
