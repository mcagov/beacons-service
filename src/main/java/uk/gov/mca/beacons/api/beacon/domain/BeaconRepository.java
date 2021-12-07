package uk.gov.mca.beacons.api.beacon.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;

public interface BeaconRepository extends JpaRepository<Beacon, BeaconId> {
  List<Beacon> getByAccountHolderId(AccountHolderId accountHolderId);
}
