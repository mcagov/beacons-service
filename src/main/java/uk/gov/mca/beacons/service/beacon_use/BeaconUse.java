package uk.gov.mca.beacons.service.beacon_use;

import com.sun.istack.NotNull;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "beacon_uses")
public class BeaconUse {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID beaconId;
  private String useType;
  private boolean mainUse;
  private UUID beaconPersonId;
  private UUID vesselId;

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
}
