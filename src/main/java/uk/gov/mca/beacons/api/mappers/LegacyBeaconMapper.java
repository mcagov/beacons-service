package uk.gov.mca.beacons.api.mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.LegacyBeaconDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;
import uk.gov.mca.beacons.api.utils.OffsetDateTimeOptionalZoneParser;

@Service
public class LegacyBeaconMapper {

  public LegacyBeacon fromDTO(LegacyBeaconDTO dto) {
    final var attributes = dto.getAttributes();
    return LegacyBeacon
      .builder()
      .beacon(attributes.getBeacon())
      .uses(attributes.getUses())
      .owner(attributes.getOwner())
      .secondaryOwners(attributes.getSecondaryOwners())
      .emergencyContact(attributes.getEmergencyContact())
      .build();
  }

  public WrapperDTO<LegacyBeaconDTO> toWrapperDTO(LegacyBeacon beacon) {
    final var wrapperDTO = new WrapperDTO<LegacyBeaconDTO>();
    wrapperDTO.setData(toDTO(beacon));

    return wrapperDTO;
  }

  public LegacyBeaconDTO toDTO(LegacyBeacon beacon) {
    final var dto = new LegacyBeaconDTO();
    dto.setId(beacon.getId());

    final var attributes = LegacyBeaconDTO.Attributes
      .builder()
      .beacon(beacon.getBeacon())
      .uses(beacon.getUses())
      .owner(beacon.getOwner())
      .secondaryOwners(beacon.getSecondaryOwners())
      .emergencyContact(beacon.getEmergencyContact())
      .build();
    dto.setAttributes(attributes);

    return dto;
  }

  public LegacyBeaconEntity toJpaEntity(LegacyBeacon legacyBeacon) {
    final var legacyBeaconEntity = new LegacyBeaconEntity();

    legacyBeaconEntity.setId(legacyBeacon.getId());

    final var hexId = (String) legacyBeacon.getBeacon().get("hexId");
    legacyBeaconEntity.setHexId(hexId);

    final var ownerEmail = (String) legacyBeacon.getOwner().get("email");
    legacyBeaconEntity.setOwnerEmail(ownerEmail);

    final var ownerName = (String) legacyBeacon.getOwner().get("ownerName");
    legacyBeaconEntity.setOwnerName(ownerName);

    final var useActivities = legacyBeacon
      .getUses()
      .stream()
      .map(use -> (String) use.getOrDefault("useType", ""))
      .collect(Collectors.joining(", "));
    legacyBeaconEntity.setUseActivities(useActivities);

    final var createdDate = OffsetDateTimeOptionalZoneParser.parse(
      (String) legacyBeacon.getBeacon().get("createdDate")
    );
    legacyBeaconEntity.setCreatedDate(createdDate);

    final var lastModifiedDate = OffsetDateTimeOptionalZoneParser.parse(
      (String) legacyBeacon.getBeacon().get("lastModifiedDate")
    );
    legacyBeaconEntity.setLastModifiedDate(lastModifiedDate);

    legacyBeaconEntity.setBeaconStatus(legacyBeacon.getBeaconStatus());

    final var data = Map.of(
      "beacon",
      legacyBeacon.getBeacon(),
      "uses",
      legacyBeacon.getUses(),
      "owner",
      legacyBeacon.getOwner(),
      "secondaryOwners",
      legacyBeacon.getSecondaryOwners(),
      "emergencyContact",
      legacyBeacon.getEmergencyContact()
    );
    legacyBeaconEntity.setData(data);

    return legacyBeaconEntity;
  }

  public LegacyBeacon fromJpaEntity(LegacyBeaconEntity beaconEntity) {
    final var data = beaconEntity.getData();
    final var beacon = data.get("beacon");
    final var uses = data.get("uses");
    final var owner = data.get("owner");
    final var secondaryOwners = data.get("secondaryOwners");
    final var emergencyContact = data.get("emergencyContact");

    return LegacyBeacon
      .builder()
      .id(beaconEntity.getId())
      .beaconStatus(beaconEntity.getBeaconStatus())
      .beacon((Map) beacon)
      .uses((List) uses)
      .owner((Map) owner)
      .secondaryOwners((List) secondaryOwners)
      .emergencyContact((Map) emergencyContact)
      .build();
  }
}
