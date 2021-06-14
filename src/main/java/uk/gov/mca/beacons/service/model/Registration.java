package uk.gov.mca.beacons.service.model;

import java.util.List;
import javax.validation.Valid;

public class Registration {

  @Valid
  private Beacon beacon;

  private List<BeaconUse> uses;

  private BeaconPerson owner;

  private List<BeaconPerson> emergencyContacts;

  public List<Beacon> getBeacon() {
    return beacons;
  }

  public void setBeacons(List<Beacon> beacons) {
    this.beacons = beacons;
  }
}
