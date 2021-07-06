package uk.gov.mca.beacons.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.domain.NoteType;

@Getter
@Setter
@Builder
public class CreateNoteRequest {

  private UUID beaconId;
  private String note;
  private NoteType type;
  private LocalDateTime createdDate;
  private UUID personId;
  private String fullName;
  private String email;
}
