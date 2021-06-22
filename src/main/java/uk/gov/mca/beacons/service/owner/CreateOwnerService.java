package uk.gov.mca.beacons.service.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.service.gateway.OwnerGateway;
import uk.gov.mca.beacons.service.mappers.CreateOwnerRequestMapper;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@Service
@Transactional
public class CreateOwnerService {

  private final OwnerGateway ownerGateway;

  @Autowired
  public CreateOwnerService(OwnerGateway ownerGateway) {
    this.ownerGateway = ownerGateway;
  }

  public BeaconPerson execute(BeaconPerson beaconPerson) {
    final var createOwnerRequest = CreateOwnerRequestMapper.fromBeaconPerson(
      beaconPerson
    );
    return ownerGateway.save(createOwnerRequest);
  }
}
