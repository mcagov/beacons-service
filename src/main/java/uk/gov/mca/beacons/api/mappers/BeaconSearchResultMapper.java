package uk.gov.mca.beacons.api.mappers;

import static uk.gov.mca.beacons.api.dto.BeaconSearchResultDTO.Attributes;

import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.BeaconSearchResultDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.utils.OffsetDateTimeOptionalZoneParser;

@Service
public class BeaconSearchResultMapper {

  public BeaconSearchResultDTO toDTO(Beacon domain) {
    final var dto = new BeaconSearchResultDTO();
    dto.setId(domain.getId());

    final BeaconSearchResultDTO.Attributes attributes = Attributes
      .builder()
      .lastModifiedDate(domain.getLastModifiedDate())
      .beaconStatus(domain.getBeaconStatus())
      .hexId(domain.getHexId())
      .ownerName(domain.getOwner().getFullName())
      .beaconUse(
        domain
          .getUses()
          .stream()
          .map(beaconUse -> String.valueOf(beaconUse.getActivity()))
          .collect(Collectors.joining(", "))
      )
      .build();

    dto.setAttributes(attributes);

    return dto;
  }

  public BeaconSearchResultDTO toDTO(LegacyBeacon domain) {
    final var dto = new BeaconSearchResultDTO();
    dto.setId(domain.getId());

    final BeaconSearchResultDTO.Attributes attributes = Attributes
      .builder()
      .lastModifiedDate(
        OffsetDateTimeOptionalZoneParser.parse(
          (String) domain.getBeacon().get("lastModifiedDate")
        )
      )
      .beaconStatus(domain.getBeaconStatus())
      .hexId((String) domain.getBeacon().get("hexId"))
      .ownerName((String) domain.getOwner().get("ownerName"))
      .beaconUse(
        domain
          .getUses()
          .stream()
          .map(beaconUse -> String.valueOf(beaconUse.get("useType")))
          .collect(Collectors.joining(", "))
      )
      .build();

    dto.setAttributes(attributes);

    return dto;
  }

  public final WrapperDTO<BeaconSearchResultDTO> toWrapperDTO(Beacon beacon) {
    final WrapperDTO<BeaconSearchResultDTO> wrapperDTO = new WrapperDTO<>();

    wrapperDTO.setData(toDTO(beacon));

    return wrapperDTO;
  }
}
