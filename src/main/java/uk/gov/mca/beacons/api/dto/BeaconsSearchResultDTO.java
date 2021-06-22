package uk.gov.mca.beacons.api.dto;

import java.util.List;
import uk.gov.mca.beacons.api.db.Beacon;

public class BeaconsSearchResultDTO {

  private List<Beacon> beacons;

  public List<Beacon> getBeacons() {
    return beacons;
  }

  public void setBeacons(List<Beacon> beacons) {
    this.beacons = beacons;
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
}
