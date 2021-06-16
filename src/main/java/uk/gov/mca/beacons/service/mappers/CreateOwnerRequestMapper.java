package uk.gov.mca.beacons.service.mappers;

import uk.gov.mca.beacons.service.gateway.CreateOwnerRequest;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;

public class CreateOwnerRequestMapper {

  public static BeaconPerson toBeaconPerson(CreateOwnerRequest request) {
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
