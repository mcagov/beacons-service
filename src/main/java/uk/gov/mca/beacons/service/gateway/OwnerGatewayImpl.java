package uk.gov.mca.beacons.service.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;

@Repository
public class OwnerGatewayImpl implements OwnerGateway {

  private final BeaconPersonRepository beaconPersonRepository;

  @Autowired
  public OwnerGatewayImpl(BeaconPersonRepository beaconPersonRepository) {
    this.beaconPersonRepository = beaconPersonRepository;
  }

  public void createOwner(CreateOwnerRequest request) {
    final BeaconPerson owner = getOwnerFromRequest(request);
    beaconPersonRepository.save(owner);
  }

  private BeaconPerson getOwnerFromRequest(CreateOwnerRequest request) {
    final BeaconPerson owner = new BeaconPerson();
    owner.setBeaconId(request.getBeaconId());
    owner.setFullName(request.getFullName());
    owner.setTelephoneNumber(request.getTelephoneNumber());
    owner.setAlternativeTelephoneNumber(
      request.getAlternativeTelephoneNumber()
    );
    owner.setEmail(request.getEmail());
    owner.setPersonType(PersonType.OWNER);
    owner.setAddressLine1(request.getAddressLine1());
    owner.setAddressLine2(request.getAddressLine2());
    owner.setAddressLine3(request.getAddressLine3());
    owner.setAddressLine4(request.getAddressLine4());
    owner.setTownOrCity(request.getTownOrCity());
    owner.setPostcode(request.getPostcode());
    owner.setCounty(request.getCounty());

    return owner;
  }
}
