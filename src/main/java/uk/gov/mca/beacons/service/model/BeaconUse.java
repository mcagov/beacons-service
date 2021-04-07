package uk.gov.mca.beacons.service.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class BeaconUse {

  @Id
  @GeneratedValue
  private UUID id;

  private UUID beaconId;

  @Enumerated(EnumType.STRING)
  private Environment environment;

  private String otherEnvironmentUse;

  @Enumerated(EnumType.STRING)
  private Purpose purpose;

  @Enumerated(EnumType.STRING)
  private Activity activity;

  private String otherActivity;

  private String callSign;

  private String description;

  private String vesselMmsi;

  private String radioComms;

  private Integer maxCapacity;

  private String vesselType;

  private String ssrNumber;

  private String homeport;

  private String areaOfOperation;

  private boolean mainUse;

  private String beaconPosition;

  private String aircraftManufacturer;

  private String principalAirport;

  private String secondaryAirport;

  private String registrationMark;

  private String hexAddress;

  private String cnOrMsnNumber;

  private Boolean dongle;

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

  public Environment getEnvironment() {
    return environment;
  }

  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  public String getOtherEnvironmentUse() {
    return otherEnvironmentUse;
  }

  public void setOtherEnvironmentUse(String otherEnvironmentUse) {
    this.otherEnvironmentUse = otherEnvironmentUse;
  }

  public Purpose getPurpose() {
    return purpose;
  }

  public void setPurpose(Purpose purpose) {
    this.purpose = purpose;
  }

  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public String getOtherActivity() {
    return otherActivity;
  }

  public void setOtherActivity(String otherActivity) {
    this.otherActivity = otherActivity;
  }

  public String getCallSign() {
    return callSign;
  }

  public void setCallSign(String callSign) {
    this.callSign = callSign;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVesselMmsi() {
    return vesselMmsi;
  }

  public void setVesselMmsi(String vesselMmsi) {
    this.vesselMmsi = vesselMmsi;
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

  public String getSsrNumber() {
    return ssrNumber;
  }

  public void setSsrNumber(String ssrNumber) {
    this.ssrNumber = ssrNumber;
  }

  public String getHomeport() {
    return homeport;
  }

  public void setHomeport(String homeport) {
    this.homeport = homeport;
  }

  public String getAreaOfOperation() {
    return areaOfOperation;
  }

  public void setAreaOfOperation(String areaOfOperation) {
    this.areaOfOperation = areaOfOperation;
  }

  public boolean isMainUse() {
    return mainUse;
  }

  public void setMainUse(boolean mainUse) {
    this.mainUse = mainUse;
  }

  public String getBeaconPosition() {
    return beaconPosition;
  }

  public void setBeaconPosition(String beaconPosition) {
    this.beaconPosition = beaconPosition;
  }

  public String getAircraftManufacturer() {
    return aircraftManufacturer;
  }

  public void setAircraftManufacturer(String aircraftManufacturer) {
    this.aircraftManufacturer = aircraftManufacturer;
  }

  public String getPrincipalAirport() {
    return principalAirport;
  }

  public void setPrincipalAirport(String principalAirport) {
    this.principalAirport = principalAirport;
  }

  public String getSecondaryAirport() {
    return secondaryAirport;
  }

  public void setSecondaryAirport(String secondaryAirport) {
    this.secondaryAirport = secondaryAirport;
  }

  public String getRegistrationMark() {
    return registrationMark;
  }

  public void setRegistrationMark(String registrationMark) {
    this.registrationMark = registrationMark;
  }

  public String getHexAddress() {
    return hexAddress;
  }

  public void setHexAddress(String hexAddress) {
    this.hexAddress = hexAddress;
  }

  public String getCnOrMsnNumber() {
    return cnOrMsnNumber;
  }

  public void setCnOrMsnNumber(String cnOrMsnNumber) {
    this.cnOrMsnNumber = cnOrMsnNumber;
  }

  public Boolean getDongle() {
    return dongle;
  }

  public void setDongle(Boolean dongle) {
    this.dongle = dongle;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
