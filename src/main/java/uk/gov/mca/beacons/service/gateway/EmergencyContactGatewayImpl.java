package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.db.Person;
import uk.gov.mca.beacons.service.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.service.mappers.CreateEmergencyContactRequestMapper;

@Repository
@Transactional
public class EmergencyContactGatewayImpl implements EmergencyContactGateway {

    private final BeaconPersonJpaRepository beaconPersonJpaRepository;

    @Autowired
    public EmergencyContactGatewayImpl(
            BeaconPersonJpaRepository beaconPersonJpaRepository
    ) {
        this.beaconPersonJpaRepository = beaconPersonJpaRepository;
    }

    @Override
    public void save(CreateEmergencyContactRequest request) {
        final Person emergencyContact = CreateEmergencyContactRequestMapper.toBeaconPerson(
                request
        );
        beaconPersonJpaRepository.save(emergencyContact);
    }

    @Override
    public List<Person> findAllByBeaconId(UUID beaconId) {
        return beaconPersonJpaRepository.findEmergencyContactsByBeaconId(beaconId);
    }
}
