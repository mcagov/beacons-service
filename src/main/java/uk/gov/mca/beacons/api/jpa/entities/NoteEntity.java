package uk.gov.mca.beacons.api.jpa.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.domain.NoteType;

@Entity(name = "note")
@Getter
@Setter
public class NoteEntity {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID beaconId;

  private String text;

  @Enumerated(EnumType.STRING)
  private NoteType type;

  private LocalDateTime createdDate;

  private UUID personId;

  private String fullName;

  private String email;
}
