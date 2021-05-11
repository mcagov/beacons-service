package uk.gov.mca.beacons.service.mappers;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconStatus;

@Service
public class BeaconMapper {

  public BeaconDTO toDTO(Beacon domain) {
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

  public Beacon fromDTO(BeaconDTO dto) {
    final var attributes = dto.getAttributes();

    final var beacon = new Beacon();
    beacon.setId(dto.getId());
    beacon.setHexId((String) attributes.get("hexId"));
    beacon.setManufacturer((String) attributes.get("manufacturer"));
    beacon.setModel((String) attributes.get("model"));
    beacon.setChkCode((String) attributes.get("chkCode"));
    beacon.setManufacturerSerialNumber(
      (String) attributes.get("manufacturerSerialNumber")
    );

    beacon.setBeaconStatus(getStatusOrNull("status", attributes));

    beacon.setCreatedDate(getDateOrNull("createdDate", attributes));
    beacon.setBatteryExpiryDate(getDateOrNull("batteryExpiryDate", attributes));
    beacon.setLastServicedDate(getDateOrNull("lastServicedDate", attributes));

    return beacon;
  }

  private static LocalDateTime getDateOrNull(
    String key,
    Map<String, Object> attributes
  ) {
    var attributeValue = attributes.get(key);
    if (attributeValue == null) return null;

    var result = LocalDateTime.parse((String) attributeValue);
    return result;
  }

  private BeaconStatus getStatusOrNull(
    String key,
    Map<String, Object> attributes
  ) {
    var attributeValue = attributes.get(key);
    if (attributeValue == null) return null;

    var result = BeaconStatus.valueOf((String) attributeValue);

    return result;
  }
}
