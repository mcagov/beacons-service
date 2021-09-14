package uk.gov.mca.beacons.api.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.mca.beacons.api.domain.NoteType;

public class NoteDTO extends DomainDTO<NoteDTO.Attributes> {

  private String type = "note";

  public String getType() {
    return type;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Attributes {

    private UUID beaconId;
    private UUID legacyBeaconId;
    private String text;
    private NoteType type;
    private OffsetDateTime createdDate;
    private UUID userId;
    private String fullName;
    private String email;
  }
}
