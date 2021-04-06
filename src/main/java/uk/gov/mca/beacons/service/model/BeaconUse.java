package uk.gov.mca.beacons.service.model;

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
public class BeaconUse {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID beaconId;

  private String useType;

  private boolean mainUse;

  private UUID beaconPersonId;

  private UUID vesselId;

  private String beaconPosition;

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

  public UUID getBeaconId() {
    return beaconId;
  }

  public void setBeaconId(UUID beaconId) {
    this.beaconId = beaconId;
  }

  public String getUseType() {
    return useType;
  }

  public void setUseType(String useType) {
    this.useType = useType;
  }

  public boolean isMainUse() {
    return mainUse;
  }

  public void setMainUse(boolean mainUse) {
    this.mainUse = mainUse;
  }

  public UUID getBeaconPersonId() {
    return beaconPersonId;
  }

  public void setBeaconPersonId(UUID beaconPersonId) {
    this.beaconPersonId = beaconPersonId;
  }

  public UUID getVesselId() {
    return vesselId;
  }

  public void setVesselId(UUID vesselId) {
    this.vesselId = vesselId;
  }

  public String getBeaconPosition() {
    return beaconPosition;
  }

  public void setBeaconPosition(String location) {
    this.beaconPosition = location;
  }
}
