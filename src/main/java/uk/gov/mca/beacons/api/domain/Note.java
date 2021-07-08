package uk.gov.mca.beacons.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Note {

  private UUID id;
  private UUID beaconId;
  private String text;
  private NoteType type;
  private LocalDateTime createdDate;
  private UUID personId;
  private String fullName;
  private String email;
}
