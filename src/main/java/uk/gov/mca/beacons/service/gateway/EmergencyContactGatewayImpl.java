package uk.gov.mca.beacons.service.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;
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
    final BeaconPerson emergencyContact = getEmergencyContactFromRequest(
      request
    );
    beaconPersonRepository.save(emergencyContact);
  }

  private BeaconPerson getEmergencyContactFromRequest(
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
