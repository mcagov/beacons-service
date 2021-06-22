package uk.gov.mca.beacons.service.gateway;

import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.db.Person;
import uk.gov.mca.beacons.service.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.service.mappers.CreateOwnerRequestMapper;

@Repository
@Transactional
public class OwnerGatewayImpl implements OwnerGateway {

    private final BeaconPersonJpaRepository beaconPersonJpaRepository;

    @Autowired
    public OwnerGatewayImpl(BeaconPersonJpaRepository beaconPersonJpaRepository) {
        this.beaconPersonJpaRepository = beaconPersonJpaRepository;
    }

    @Override
    public void save(CreateOwnerRequest request) {
        final Person owner = CreateOwnerRequestMapper.toBeaconPerson(request);
        beaconPersonJpaRepository.save(owner);
    }

    @Override
    public Person findByBeaconId(UUID beaconId) {
        return beaconPersonJpaRepository.findOwnerByBeaconId(beaconId);
    }
}
