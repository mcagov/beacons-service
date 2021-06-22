package uk.gov.mca.beacons.service.mappers;

import java.util.UUID;
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

  public static CreateOwnerRequest fromBeaconPerson(
    BeaconPerson owner,
    UUID beaconId
  ) {
    return CreateOwnerRequest
      .builder()
      .beaconId(beaconId)
      .fullName(owner.getFullName())
      .telephoneNumber(owner.getTelephoneNumber())
      .alternativeTelephoneNumber(owner.getAlternativeTelephoneNumber())
      .email(owner.getEmail())
      .addressLine1(owner.getAddressLine1())
      .addressLine2(owner.getAddressLine2())
      .addressLine3(owner.getAddressLine3())
      .addressLine4(owner.getAddressLine4())
      .townOrCity(owner.getTownOrCity())
      .postcode(owner.getPostcode())
      .county(owner.getCounty())
      .build();
  }

  public static CreateOwnerRequest fromBeaconPerson(BeaconPerson owner) {
    return fromBeaconPerson(owner, null);
  }
}
