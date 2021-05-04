package uk.gov.mca.beacons.service.dto;

import uk.gov.mca.beacons.service.model.BeaconUse;

public class BeaconUseDTO extends DomainDTO {

  private String type = "beaconUse";

  public String getType() {
    return type;
  }

  public static BeaconUseDTO from(BeaconUse domain) {
    var dto = new BeaconUseDTO();
    dto.setId(domain.getId());
    dto.addAttribute("environment", domain.getEnvironment());
    dto.addAttribute("activity", domain.getActivity());
    dto.addAttribute("moreDetails", domain.getMoreDetails());
    return dto;
  }
}
