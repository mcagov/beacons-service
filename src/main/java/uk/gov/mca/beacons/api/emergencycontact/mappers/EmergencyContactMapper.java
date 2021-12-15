package uk.gov.mca.beacons.api.emergencycontact.mappers;

import org.springframework.stereotype.Component;
import uk.gov.mca.beacons.api.emergencycontact.domain.EmergencyContact;
import uk.gov.mca.beacons.api.emergencycontact.rest.CreateEmergencyContactDTO;

@Component("EmergencyContactMapperV2")
public class EmergencyContactMapper {

  public EmergencyContact fromDTO(CreateEmergencyContactDTO dto) {
    EmergencyContact emergencyContact = new EmergencyContact();
    emergencyContact.setFullName(dto.getFullName());
    emergencyContact.setTelephoneNumber(dto.getTelephoneNumber());
    emergencyContact.setAlternativeTelephoneNumber(
      dto.getAlternativeTelephoneNumber()
    );

    return emergencyContact;
  }
}
