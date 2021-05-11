package uk.gov.mca.beacons.service.mappers;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.model.Beacon;

@Service
public class BeaconMapper {

  public BeaconDTO from(Beacon domain) {
    final var dto = new BeaconDTO();
    dto.setId(domain.getId());
    dto.addAttribute("hexId", domain.getHexId());
    dto.addAttribute("status", domain.getBeaconStatus());
    dto.addAttribute("manufacturer", domain.getManufacturer());
    dto.addAttribute("createdDate", domain.getCreatedDate());
    dto.addAttribute("model", domain.getModel());
    dto.addAttribute(
      "manufacturerSerialNumber",
      domain.getManufacturerSerialNumber()
    );
    dto.addAttribute("chkCode", domain.getChkCode());
    dto.addAttribute("batteryExpiryDate", domain.getBatteryExpiryDate());
    dto.addAttribute("lastServicedDate", domain.getLastServicedDate());

    return dto;
  }
  // public static Beacon to(BeaconDTO dto) {

  //   final var updates = dto.getAttributes();

  //   updates.get(key)
  //   final var beacon = new Beacon();
  //   beacon.setId(dto.getId());
  //   beacon.setHexId((String) updates.get("hexId"));
  //   beacon.setBeaconStatus((String) updates.get("status"));
  //   beacon.setManufacturer((String) updates.get("manufacturer"));
  //   beacon.setCreatedDate((String) updates.get("createdDate"));
  //   beacon.setModel((String) updates.get("model"));
  //   beacon.setManufacturerSerialNumber((String) updates.get(
  //     "manufacturerSerialNumber"
  //   ));
  //   beacon.setChkCode("chkCode"));
  //   beacon.setBatteryExpiryDate((String) updates.get("batteryExpiryDate"));
  //   beacon.setLastServicedDate((String) updates.get("lastServicedDate"));

  //   return beacon;
  // }
}
