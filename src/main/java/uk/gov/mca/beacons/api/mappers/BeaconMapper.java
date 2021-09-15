package uk.gov.mca.beacons.api.mappers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.dto.BeaconDTO;
import uk.gov.mca.beacons.api.hateoas.BeaconLinkStrategy;
import uk.gov.mca.beacons.api.hateoas.HateoasLinkManager;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@Service
public class BeaconMapper extends BaseMapper {

  final HateoasLinkManager<Beacon> linkManager;
  final BeaconLinkStrategy linkStrategy;

  @Autowired
  public BeaconMapper(
    HateoasLinkManager<Beacon> linkManager,
    BeaconLinkStrategy linkStrategy
  ) {
    this.linkManager = linkManager;
    this.linkStrategy = linkStrategy;
  }

  public BeaconDTO toDTO(Beacon domain) {
    final var dto = new BeaconDTO();
    dto.setId(domain.getId());

    final Map<String, Object> attributes = new HashMap<>();
    attributes.put("hexId", domain.getHexId());
    attributes.put("status", domain.getBeaconStatus());
    attributes.put("manufacturer", domain.getManufacturer());
    attributes.put("createdDate", domain.getCreatedDate());
    attributes.put("model", domain.getModel());
    attributes.put(
      "manufacturerSerialNumber",
      domain.getManufacturerSerialNumber()
    );
    attributes.put("chkCode", domain.getChkCode());
    attributes.put("batteryExpiryDate", domain.getBatteryExpiryDate());
    attributes.put("lastServicedDate", domain.getLastServicedDate());
    attributes.put("referenceNumber", domain.getReferenceNumber());
    attributes.put("lastModifiedDate", domain.getLastModifiedDate());
    attributes.put("accountHolderId", domain.getAccountHolderId());
    attributes.put("mti", domain.getMti());
    attributes.put("svdr", domain.getSvdr());
    dto.setAttributes(attributes);
    dto.addLinks(linkManager.getLinksFor(domain, linkStrategy));
    return dto;
  }

  public Beacon fromDTO(BeaconDTO dto) {
    final var attributes = dto.getAttributes();

    final var beacon = new Beacon();
    beacon.setId(dto.getId());
    beacon.setHexId((String) attributes.get("hexId"));
    beacon.setManufacturer((String) attributes.get("manufacturer"));
    beacon.setModel((String) attributes.get("model"));
    beacon.setMti((String) attributes.get("mti"));
    beacon.setSvdr((String) attributes.get("svdr") == "true");
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
