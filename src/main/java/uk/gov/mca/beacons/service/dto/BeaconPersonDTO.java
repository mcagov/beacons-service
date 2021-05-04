package uk.gov.mca.beacons.service.dto;

import uk.gov.mca.beacons.service.model.BeaconPerson;

public class BeaconPersonDTO extends DomainDTO {

  private String type = "beaconPerson";

  public String getType() {
    return type;
  }

  public static BeaconPersonDTO from(BeaconPerson domain) {
    var dto = new BeaconPersonDTO();
    dto.setId(domain.getId());
    dto.AddAttribute("fullName", domain.getFullName());
    dto.AddAttribute("email", domain.getEmail());
    dto.AddAttribute("telephoneNumber", domain.getTelephoneNumber());
    dto.AddAttribute("addressLine1", domain.getAddressLine1());
    dto.AddAttribute("addressLine2", domain.getAddressLine2());
    dto.AddAttribute("addressLine3", domain.getAddressLine3());
    dto.AddAttribute("addressLine4", domain.getAddressLine4());
    dto.AddAttribute("townOrCity", domain.getTownOrCity());
    dto.AddAttribute("county", domain.getCounty());
    dto.AddAttribute("postcode", domain.getPostcode());
    return dto;
  }
}
