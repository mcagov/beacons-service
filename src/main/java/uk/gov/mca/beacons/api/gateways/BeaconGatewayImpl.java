package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.db.Beacon;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;

@Repository
@Transactional
public class BeaconGatewayImpl implements BeaconGateway {

    private final BeaconJpaRepository beaconJpaRepository;

    @Autowired
    public BeaconGatewayImpl(BeaconJpaRepository beaconJpaRepository) {
        this.beaconJpaRepository = beaconJpaRepository;
    }

    @Override
    public List<Beacon> findAllByAccountHolderId(UUID accountId) {
        return beaconJpaRepository.findAllByAccountHolderId(accountId);
    }
}
