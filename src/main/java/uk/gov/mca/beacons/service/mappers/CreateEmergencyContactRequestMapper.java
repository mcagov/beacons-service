package uk.gov.mca.beacons.service.mappers;

import java.util.UUID;
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

  public static CreateEmergencyContactRequest fromBeaconPerson(
    BeaconPerson emergencyContact,
    UUID beaconId
  ) {
    return CreateEmergencyContactRequest
      .builder()
      .beaconId(beaconId)
      .fullName(emergencyContact.getFullName())
      .telephoneNumber(emergencyContact.getTelephoneNumber())
      .alternativeTelephoneNumber(
        emergencyContact.getAlternativeTelephoneNumber()
      )
      .email(emergencyContact.getEmail())
      .addressLine1(emergencyContact.getAddressLine1())
      .addressLine2(emergencyContact.getAddressLine2())
      .addressLine3(emergencyContact.getAddressLine3())
      .addressLine4(emergencyContact.getAddressLine4())
      .townOrCity(emergencyContact.getTownOrCity())
      .postcode(emergencyContact.getPostcode())
      .county(emergencyContact.getCounty())
      .build();
  }
}
