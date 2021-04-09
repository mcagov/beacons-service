package uk.gov.mca.beacons.service.model;

import java.util.List;
import javax.validation.Valid;

/**
 * Class representing a registration object.
 *
 * TODO: Determine how to persist reference number and registration object.
 */
public class Registration {

  @Valid
  private List<Beacon> beacons;

  public List<Beacon> getBeacons() {
    return beacons;
  }

  public void setBeacons(List<Beacon> beacons) {
    this.beacons = beacons;
  }
}
