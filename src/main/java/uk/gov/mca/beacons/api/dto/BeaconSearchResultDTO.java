package uk.gov.mca.beacons.api.dto;

import static uk.gov.mca.beacons.api.dto.BeaconSearchResultDTO.Attributes;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.mca.beacons.api.domain.BeaconStatus;

public class BeaconSearchResultDTO extends DomainDTO<Attributes> {

  private String type = "beaconSearchResult";

  public String getType() {
    return type;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Attributes {

    private OffsetDateTime lastModifiedDate;
    private BeaconStatus beaconStatus;
    private String hexId;
    private String ownerName;
    private String beaconUse;
  }
}
