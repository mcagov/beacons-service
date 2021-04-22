package uk.gov.mca.beacons.service.model;

import java.util.List;

public class BeaconsSearchResult {

  private List<Beacon> beacons;

  public List<Beacon> getBeacons() {
    return beacons;
  }

  public int getCount() {
    return beacons.size();
  }

  public int getPageSize() {
    return beacons.size();
  }

  public List<Beacon> getBeacons(List<Beacon> beacons) {
    return beacons;
  }

  public void setBeacons(List<Beacon> beacons) {
    this.beacons = beacons;
  }
}
