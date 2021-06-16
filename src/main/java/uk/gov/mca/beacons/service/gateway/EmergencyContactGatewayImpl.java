package uk.gov.mca.beacons.service.gateway;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.mappers.CreateEmergencyContactRequestMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;

@Repository
public class EmergencyContactGatewayImpl implements EmergencyContactGateway {

  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public EmergencyContactGatewayImpl(
    BeaconPersonRepository beaconPersonRepository
  ) {
    this.beaconPersonRepository = beaconPersonRepository;
  }

  @Override
  public void save(CreateEmergencyContactRequest request) {
    final BeaconPerson emergencyContact = CreateEmergencyContactRequestMapper.toBeaconPerson(
      request
    );
    beaconPersonRepository.save(emergencyContact);
  }

  @Override
  public List<BeaconPerson> findByBeaconId(UUID beaconId) {
    return beaconPersonRepository.findEmergencyContactsByBeaconId(beaconId);
  }
}
