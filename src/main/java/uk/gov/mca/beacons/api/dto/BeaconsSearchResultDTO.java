package uk.gov.mca.beacons.api.dto;

import java.util.List;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

public class BeaconsSearchResultDTO {

  private List<BeaconSearchResultDTO> beaconSearchResults;
  private List<Beacon> beacons;

  public List<BeaconSearchResultDTO> getBeaconSearchResults() {
    return beaconSearchResults;
  }

  public List<Beacon> getBeacons() {
    return beacons;
  }

  public void setBeaconSearchResults(
    List<BeaconSearchResultDTO> beaconSearchResults
  ) {
    this.beaconSearchResults = beaconSearchResults;
  }

  public void setBeacons(List<Beacon> beacons) {
    this.beacons = beacons;
  }

  public int getCount() {
    return beaconSearchResults.size();
  }

  public int getPageSize() {
    return beaconSearchResults.size();
  }
}
