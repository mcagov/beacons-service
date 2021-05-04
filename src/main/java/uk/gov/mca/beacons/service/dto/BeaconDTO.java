package uk.gov.mca.beacons.service.dto;

import uk.gov.mca.beacons.service.model.Beacon;

public class BeaconDTO extends DomainDTO {

  private String type = "beacon";

  public String getType() {
    return type;
  }


  public static BeaconDTO from(Beacon domain) {
    var dto = new BeaconDTO();
    dto.setId(domain.getId());
    dto.AddAttribute("hexId", domain.getHexId());
    dto.AddAttribute("status", domain.getBeaconStatus());
    dto.AddAttribute("manufacturer", domain.getManufacturer());
    dto.AddAttribute("createdDate", domain.getCreatedDate());
    dto.AddAttribute("model", domain.getModel());
    dto.AddAttribute(
      "manufacturerSerialNumber",
      domain.getManufacturerSerialNumber()
    );
    dto.AddAttribute("chkCode", domain.getChkCode());
    dto.AddAttribute("batteryExpiryDate", domain.getBatteryExpiryDate());
    dto.AddAttribute("lastServicedDate", domain.getLastServicedDate());

    return dto;
  }
}
