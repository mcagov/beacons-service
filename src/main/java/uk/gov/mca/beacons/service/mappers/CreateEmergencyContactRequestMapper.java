package uk.gov.mca.beacons.service.mappers;

import uk.gov.mca.beacons.service.gateway.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;

public class CreateEmergencyContactRequestMapper {

  public static BeaconPerson toBeaconPerson(
    CreateEmergencyContactRequest request
  ) {
    final BeaconPerson emergencyContact = new BeaconPerson();
    emergencyContact.setBeaconId(request.getBeaconId());
    emergencyContact.setFullName(request.getFullName());
    emergencyContact.setTelephoneNumber(request.getTelephoneNumber());
    emergencyContact.setAlternativeTelephoneNumber(
      request.getAlternativeTelephoneNumber()
    );
    emergencyContact.setEmail(request.getEmail());
    emergencyContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    emergencyContact.setAddressLine1(request.getAddressLine1());
    emergencyContact.setAddressLine2(request.getAddressLine2());
    emergencyContact.setAddressLine3(request.getAddressLine3());
    emergencyContact.setAddressLine4(request.getAddressLine4());
    emergencyContact.setTownOrCity(request.getTownOrCity());
    emergencyContact.setPostcode(request.getPostcode());
    emergencyContact.setCounty(request.getCounty());

    return emergencyContact;
  }
}
