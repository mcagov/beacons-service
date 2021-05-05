package uk.gov.mca.beacons.service.dto;

import uk.gov.mca.beacons.service.model.BeaconPerson;

public class BeaconPersonDTO extends DomainDTO {

  private String type = "beaconPerson";

  public String getType() {
    return type;
  }

  public static BeaconPersonDTO from(BeaconPerson domain) {
    final var dto = new BeaconPersonDTO();
    dto.setId(domain.getId());
    dto.addAttribute("fullName", domain.getFullName());
    dto.addAttribute("email", domain.getEmail());
    dto.addAttribute("telephoneNumber", domain.getTelephoneNumber());
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
