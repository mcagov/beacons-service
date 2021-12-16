package uk.gov.mca.beacons.api.beaconowner.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.mca.beacons.api.beacon.domain.BeaconId;
import uk.gov.mca.beacons.api.emergencycontact.domain.EmergencyContact;
import uk.gov.mca.beacons.api.emergencycontact.domain.EmergencyContactId;

public interface BeaconOwnerRepository
  extends JpaRepository<BeaconOwner, BeaconOwnerId> {
  void deleteAllByBeaconId(BeaconId beaconId);
}
