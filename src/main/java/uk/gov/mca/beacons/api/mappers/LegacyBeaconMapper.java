package uk.gov.mca.beacons.api.mappers;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.LegacyBeaconDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;

@Service
public class LegacyBeaconMapper {

  public LegacyBeacon fromDTO(LegacyBeaconDTO dto) {
    final var attributes = dto.getAttributes();
    return LegacyBeacon
      .builder()
      .beacon(attributes.getBeacon())
      .uses(attributes.getUses())
      .owners(attributes.getOwners())
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
      .owners(beacon.getOwners())
      .emergencyContact(beacon.getEmergencyContact())
      .build();
    dto.setAttributes(attributes);

    return dto;
  }
}
