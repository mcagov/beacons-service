package uk.gov.mca.beacons.api.emergencycontact.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.mca.beacons.api.beacon.domain.BeaconId;

public interface EmergencyContactRepository
  extends JpaRepository<EmergencyContact, EmergencyContactId> {
  void deleteAllByBeaconId(BeaconId beaconId);
}
