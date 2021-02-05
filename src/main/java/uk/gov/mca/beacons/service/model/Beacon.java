package uk.gov.mca.beacons.service.model;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
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
}
