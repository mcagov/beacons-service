package uk.gov.mca.beacons.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Beacon {

  @Id
  @GeneratedValue
  private UUID id;

  @Enumerated(EnumType.STRING)
  @NotNull
  private BeaconType beaconType;

  @NotNull
  private String hexId;

  @NotNull
  private String manufacturer;

  @NotNull
  private String model;

  @NotNull
  private String manufacturerSerialNumber;

  private String chkCode;

  private LocalDateTime batteryExpiryDate;

  private LocalDateTime lastServicedDate;

  @Enumerated(EnumType.STRING)
  private BeaconStatus beaconStatus;

  @CreatedDate
  private LocalDateTime createdDate;

  @Transient
  @Valid
  private List<BeaconUse> uses;

  @Transient
  @Valid
  private BeaconPerson owner;

  @Transient
  @Valid
  private List<BeaconPerson> emergencyContacts;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BeaconType getBeaconType() {
    return beaconType;
  }

  public void setBeaconType(BeaconType beaconType) {
    this.beaconType = beaconType;
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

  public String getChkCode() {
    return chkCode;
  }

  public void setChkCode(String chkCode) {
    this.chkCode = chkCode;
  }

  public LocalDateTime getBatteryExpiryDate() {
    return batteryExpiryDate;
  }

  public void setBatteryExpiryDate(LocalDateTime batteryExpiryDate) {
    this.batteryExpiryDate = batteryExpiryDate;
  }

  public LocalDateTime getLastServicedDate() {
    return lastServicedDate;
  }

  public void setLastServicedDate(LocalDateTime lastServicedDate) {
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
