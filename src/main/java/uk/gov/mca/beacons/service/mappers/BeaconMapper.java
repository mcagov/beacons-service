package uk.gov.mca.beacons.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.hateoas.HateoasLinkBuilder;
import uk.gov.mca.beacons.service.hateoas.HateoasLinkBuilder.SupportedMethod;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconStatus;

@Service
public class BeaconMapper extends BaseMapper {

  final HateoasLinkBuilder linkBuilder;

  @Autowired
  public BeaconMapper(HateoasLinkBuilder linkBuilder) {
    this.linkBuilder = linkBuilder;
  }

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

    dto.addLinks(linkBuilder.getLinksFor(domain));
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
    beacon.setBeaconStatus(
      parseEnumValueOrNull(attributes.get("status"), BeaconStatus.class)
    );
    beacon.setCreatedDate(getDateTimeOrNull(attributes.get("createdDate")));
    beacon.setBatteryExpiryDate(
      getDateOrNull(attributes.get("batteryExpiryDate"))
    );
    beacon.setLastServicedDate(
      getDateOrNull(attributes.get("lastServicedDate"))
    );

    return beacon;
  }
}
