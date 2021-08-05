package uk.gov.mca.beacons.api.jpa.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uk.gov.mca.beacons.api.domain.BeaconStatus;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Beacon {

  @Id
  @GeneratedValue
  private UUID id;

  @NotNull
  private String hexId;

  @NotNull
  private String manufacturer;

  @NotNull
  private String model;

  @NotNull
  private String manufacturerSerialNumber;

  private String referenceNumber;

  private String chkCode;

  private LocalDate batteryExpiryDate;

  private LocalDate lastServicedDate;

  @Enumerated(EnumType.STRING)
  private BeaconStatus beaconStatus;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  @Transient
  @Valid
  private List<BeaconUse> uses;

  private UUID accountHolderId;

  @Transient
  @Valid
  private Person owner;

  @Transient
  @Valid
  private List<Person> emergencyContacts;
}
