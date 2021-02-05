package uk.gov.mca.beacons.service.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Vessel {

  @Id
  @GeneratedValue
  private UUID id;

  private String mmsi;
  private String name;
  private String callsign;
  private String radioComms;
  private Integer capacity;
  private String vesselType;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getMmsi() {
    return mmsi;
  }

  public void setMmsi(String mmsi) {
    this.mmsi = mmsi;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCallsign() {
    return callsign;
  }

  public void setCallsign(String callsign) {
    this.callsign = callsign;
  }

  public String getRadioComms() {
    return radioComms;
  }

  public void setRadioComms(String radioComms) {
    this.radioComms = radioComms;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public String getVesselType() {
    return vesselType;
  }

  public void setVesselType(String vesselType) {
    this.vesselType = vesselType;
  }
}
