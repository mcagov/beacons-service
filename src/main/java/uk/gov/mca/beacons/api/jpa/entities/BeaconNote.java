package uk.gov.mca.beacons.api.jpa.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.domain.NoteType;

@Entity
@Getter
@Setter
public class BeaconNote {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID beaconId;

  private String note;

  //  Should be noteType (like personType)
  //  But note.type would maybe be nicer than note.noteType?
  @Enumerated(EnumType.STRING)
  private NoteType type;

  private LocalDateTime createdDate;

  private UUID personId;

  private String fullName;

  @Email
  private String email;
}
