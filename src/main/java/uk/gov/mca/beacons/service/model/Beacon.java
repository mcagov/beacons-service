package uk.gov.mca.beacons.service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "beacons")
public class Beacon {

  @Id
  @GeneratedValue
  private UUID id;

  private String beaconType;
  private String hexId;
  private String manufacturer;
  private String model;
  private String serialNumber;
  private LocalDate batteryExpiry;
  private LocalDate lastServiced;
  private String chkCode;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getId() {
    return id;
  }

  public String getBeaconType() {
    return beaconType;
  }

  public void setBeaconType(String beaconType) {
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

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public LocalDate getBatteryExpiry() {
    return batteryExpiry;
  }

  public void setBatteryExpiry(LocalDate batteryExpiry) {
    this.batteryExpiry = batteryExpiry;
  }

  public LocalDate getLastServiced() {
    return lastServiced;
  }

  public void setLastServiced(LocalDate lastServiced) {
    this.lastServiced = lastServiced;
  }

  public String getChkCode() {
    return chkCode;
  }

  public void setChkCode(String chkCode) {
    this.chkCode = chkCode;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
}
