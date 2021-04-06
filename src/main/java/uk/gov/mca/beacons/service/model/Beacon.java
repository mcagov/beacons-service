package uk.gov.mca.beacons.service.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

  @Enumerated(EnumType.STRING)
  private BeaconType beaconType;

  private String hexId;

  private String manufacturer;

  private String model;

  private String serialNumber;

  private LocalDate batteryExpiryDate;

  private LocalDate lastServicedDate;

  private String checksum;

  private String beaconStatus;

  private String coding;

  private String protocolCode;

  @CreatedDate
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

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

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
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

  public String getChecksum() {
    return checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }

  public String getBeaconStatus() {
    return beaconStatus;
  }

  public void setBeaconStatus(String beaconStatus) {
    this.beaconStatus = beaconStatus;
  }

  public String getCoding() {
    return coding;
  }

  public void setCoding(String coding) {
    this.coding = coding;
  }

  public String getProtocolCode() {
    return protocolCode;
  }

  public void setProtocolCode(String protocolCode) {
    this.protocolCode = protocolCode;
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
