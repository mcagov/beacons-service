package uk.gov.mca.beacons.service.mappers;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.model.BeaconPerson;

@Service
public class BeaconPersonMapper {

  public BeaconPersonDTO toDTO(BeaconPerson domain) {
    final var dto = new BeaconPersonDTO();
    dto.setId(domain.getId());
    dto.addAttribute("fullName", domain.getFullName());
    dto.addAttribute("email", domain.getEmail());
    dto.addAttribute("telephoneNumber", domain.getTelephoneNumber());
    dto.addAttribute(
      "alternativeTelephoneNumber",
      domain.getAlternativeTelephoneNumber()
    );
    dto.addAttribute("addressLine1", domain.getAddressLine1());
    dto.addAttribute("addressLine2", domain.getAddressLine2());
    dto.addAttribute("addressLine3", domain.getAddressLine3());
    dto.addAttribute("addressLine4", domain.getAddressLine4());
    dto.addAttribute("townOrCity", domain.getTownOrCity());
    dto.addAttribute("county", domain.getCounty());
    dto.addAttribute("postcode", domain.getPostcode());
    return dto;
  }
}
