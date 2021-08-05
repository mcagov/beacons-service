package uk.gov.mca.beacons.api.dto;

import static uk.gov.mca.beacons.api.dto.BeaconSearchResultDTO.Attributes;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
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

    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    private BeaconStatus beaconStatus;
    private String hexId;
    private String ownerName;
    private String beaconUse;
  }
}
