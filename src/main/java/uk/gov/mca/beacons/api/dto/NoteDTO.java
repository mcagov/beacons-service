package uk.gov.mca.beacons.api.dto;

import java.time.LocalDateTime;
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
    private String text;
    private NoteType type;
    private LocalDateTime createdDate;
    private UUID userId;
    private String fullName;
    private String email;
  }
}
