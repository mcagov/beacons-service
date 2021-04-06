package uk.gov.mca.beacons.service.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Vessel {

  @Id
  @GeneratedValue
  private UUID id;

  private String description;

  private Integer vesselMmsi;

  private Integer portableMsi;

  private String name;

  private String callsign;

  private String radioComms;

  private Integer maxCapacity;

  private String vesselType;

  private String uksrId;

  private String safetraxId;

  private String homeport;

  private String areaOfUse;

  @CreatedDate
  private LocalDateTime createdDate;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getVesselMmsi() {
    return vesselMmsi;
  }

  public void setVesselMmsi(Integer vesselMmsi) {
    this.vesselMmsi = vesselMmsi;
  }

  public Integer getPortableMsi() {
    return portableMsi;
  }

  public void setPortableMsi(Integer portableMsi) {
    this.portableMsi = portableMsi;
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

  public Integer getMaxCapacity() {
    return maxCapacity;
  }

  public void setMaxCapacity(Integer maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  public String getVesselType() {
    return vesselType;
  }

  public void setVesselType(String vesselType) {
    this.vesselType = vesselType;
  }

  public String getUksrId() {
    return uksrId;
  }

  public void setUksrId(String uksrId) {
    this.uksrId = uksrId;
  }

  public String getSafetraxId() {
    return safetraxId;
  }

  public void setSafetraxId(String safetraxId) {
    this.safetraxId = safetraxId;
  }

  public String getHomeport() {
    return homeport;
  }

  public void setHomeport(String homeport) {
    this.homeport = homeport;
  }

  public String getAreaOfUse() {
    return areaOfUse;
  }

  public void setAreaOfUse(String areaOfUse) {
    this.areaOfUse = areaOfUse;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
