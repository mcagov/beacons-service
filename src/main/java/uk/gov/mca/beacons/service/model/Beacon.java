package uk.gov.mca.beacons.service.model;

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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
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

  @Transient
  @NotEmpty
  @Valid
  private List<BeaconUse> uses;

  //  private UUID ownerId;
  //  private UUID accountId;
  //  Delete below ----
  @Transient
  @Valid
  private BeaconPerson owner;

  @Transient
  @NotEmpty
  @Valid
  private List<BeaconPerson> emergencyContacts;

  // -----
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getHexId() {
    return hexId;
  }

  public void setHexId(String hexId) {
    this.hexId = hexId;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getManufacturerSerialNumber() {
    return manufacturerSerialNumber;
  }

  public void setManufacturerSerialNumber(String manufacturerSerialNumber) {
    this.manufacturerSerialNumber = manufacturerSerialNumber;
  }

  public String getReferenceNumber() {
    return referenceNumber;
  }

  public void setReferenceNumber(String reference) {
    this.referenceNumber = reference;
  }

  public String getChkCode() {
    return chkCode;
  }

  public void setChkCode(String chkCode) {
    this.chkCode = chkCode;
  }

  public LocalDate getBatteryExpiryDate() {
    return batteryExpiryDate;
  }

  public void setBatteryExpiryDate(LocalDate batteryExpiryDate) {
    this.batteryExpiryDate = batteryExpiryDate;
  }

  public LocalDate getLastServicedDate() {
    return lastServicedDate;
  }

  public void setLastServicedDate(LocalDate lastServicedDate) {
    this.lastServicedDate = lastServicedDate;
  }

  public BeaconStatus getBeaconStatus() {
    return beaconStatus;
  }

  public void setBeaconStatus(BeaconStatus beaconStatus) {
    this.beaconStatus = beaconStatus;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public List<BeaconUse> getUses() {
    return uses;
  }

  public void setUses(List<BeaconUse> uses) {
    this.uses = uses;
  }

  public BeaconPerson getOwner() {
    return owner;
  }

  public void setOwner(BeaconPerson owner) {
    this.owner = owner;
  }

  public List<BeaconPerson> getEmergencyContacts() {
    return emergencyContacts;
  }

  public void setEmergencyContacts(List<BeaconPerson> emergencyContacts) {
    this.emergencyContacts = emergencyContacts;
  }
}
