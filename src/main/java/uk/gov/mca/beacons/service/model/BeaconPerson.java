package uk.gov.mca.beacons.service.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class BeaconPerson {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID beaconId;

  private UUID personId;

  private PersonType personType;

  @CreatedDate
  private LocalDateTime createdDate;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getBeaconId() {
    return beaconId;
  }

  public void setBeaconId(UUID beaconId) {
    this.beaconId = beaconId;
  }

  public UUID getPersonId() {
    return personId;
  }

  public void setPersonId(UUID personId) {
    this.personId = personId;
  }

  public PersonType getPersonType() {
    return personType;
  }

  public void setPersonType(PersonType personType) {
    this.personType = personType;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
