package uk.gov.mca.beacons.api.registration.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwner;
import uk.gov.mca.beacons.api.beaconuse.domain.BeaconUse;
import uk.gov.mca.beacons.api.emergencycontact.domain.EmergencyContact;

@Getter
@Setter
public class Registration {

  private Beacon beacon;
  private List<BeaconUse> beaconUses;
  private BeaconOwner beaconOwner;
  private List<EmergencyContact> emergencyContacts;
}
