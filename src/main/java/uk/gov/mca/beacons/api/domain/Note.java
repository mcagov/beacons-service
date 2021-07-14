package uk.gov.mca.beacons.api.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

  private UUID id;
  private UUID beaconId;
  private String text;
  private NoteType type;
  private LocalDateTime createdDate;
  private User user;
}
