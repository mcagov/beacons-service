package uk.gov.mca.beacons.api.mappers;

import java.time.LocalDateTime;
import java.util.UUID;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.jpa.entities.Person;

public class CreateEmergencyContactRequestMapper {

  public static Person toBeaconPerson(CreateEmergencyContactRequest request) {
    final var now = LocalDateTime.now();
    final Person emergencyContact = new Person();
    emergencyContact.setBeaconId(request.getBeaconId());
    emergencyContact.setAccountHolderId(request.getAccountHolderId());
    emergencyContact.setCreatedDate(now);
    emergencyContact.setLastModifiedDate(now);
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
    Person emergencyContact,
    UUID beaconId
  ) {
    return CreateEmergencyContactRequest
      .builder()
      .beaconId(beaconId)
      .accountHolderId(emergencyContact.getAccountHolderId())
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
