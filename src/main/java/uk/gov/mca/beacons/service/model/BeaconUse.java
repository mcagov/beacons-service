package uk.gov.mca.beacons.service.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
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
  @NotNull
  private Environment environment;

  private String otherEnvironment;

  @Enumerated(EnumType.STRING)
  private Purpose purpose;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Activity activity;

  private String otherActivity;

  private String callSign;

  private Boolean vhfRadio;

  private Boolean fixedVhfRadio;

  private String fixedVhfRadioInput;

  private Boolean portableVhfRadio;

  private String portableVhfRadioInput;

  private Boolean satelliteTelephone;

  private String satelliteTelephoneInput;

  private Boolean mobileTelephone;

  @Column(name = "mobile_telephone_1")
  private String mobileTelephoneInput1;

  @Column(name = "mobile_telephone_2")
  private String mobileTelephoneInput2;

  private String description;

  private String vesselMmsi;

  private String radioComms;

  private Integer maxCapacity;

  private String vesselName;

  private String portLetterNumber;

  private String ssrNumber;

  private String homeport;

  private String areaOfOperation;

  private String beaconLocation;

  private boolean mainUse;

  private String beaconPosition;

  private String aircraftManufacturer;

  private String principalAirport;

  private String secondaryAirport;

  private String registrationMark;

  private String hexAddress;

  private String cnOrMsnNumber;

  private Boolean dongle;

  @NotNull
  private String moreDetails;

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

  public String getOtherEnvironment() {
    return otherEnvironment;
  }

  public void setOtherEnvironment(String otherEnvironment) {
    this.otherEnvironment = otherEnvironment;
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

  public Boolean getVhfRadio() {
    return vhfRadio;
  }

  public void setVhfRadio(Boolean vhfRadio) {
    this.vhfRadio = vhfRadio;
  }

  public Boolean getFixedVhfRadio() {
    return fixedVhfRadio;
  }

  public void setFixedVhfRadio(Boolean fixedVhfRadio) {
    this.fixedVhfRadio = fixedVhfRadio;
  }

  public String getFixedVhfRadioInput() {
    return fixedVhfRadioInput;
  }

  public void setFixedVhfRadioInput(String fixedVhfRadioInput) {
    this.fixedVhfRadioInput = fixedVhfRadioInput;
  }

  public Boolean getPortableVhfRadio() {
    return portableVhfRadio;
  }

  public void setPortableVhfRadio(Boolean portableVhfRadio) {
    this.portableVhfRadio = portableVhfRadio;
  }

  public String getPortableVhfRadioInput() {
    return portableVhfRadioInput;
  }

  public void setPortableVhfRadioInput(String portableVhfRadioInput) {
    this.portableVhfRadioInput = portableVhfRadioInput;
  }

  public Boolean getSatelliteTelephone() {
    return satelliteTelephone;
  }

  public void setSatelliteTelephone(Boolean satelliteTelephone) {
    this.satelliteTelephone = satelliteTelephone;
  }

  public String getSatelliteTelephoneInput() {
    return satelliteTelephoneInput;
  }

  public void setSatelliteTelephoneInput(String satelliteTelephoneInput) {
    this.satelliteTelephoneInput = satelliteTelephoneInput;
  }

  public Boolean getMobileTelephone() {
    return mobileTelephone;
  }

  public void setMobileTelephone(Boolean mobileTelephone) {
    this.mobileTelephone = mobileTelephone;
  }

  public String getMobileTelephoneInput1() {
    return mobileTelephoneInput1;
  }

  public void setMobileTelephoneInput1(String mobileTelephoneInput1) {
    this.mobileTelephoneInput1 = mobileTelephoneInput1;
  }

  public String getMobileTelephoneInput2() {
    return mobileTelephoneInput2;
  }

  public void setMobileTelephoneInput2(String mobileTelephoneInput2) {
    this.mobileTelephoneInput2 = mobileTelephoneInput2;
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

  public String getVesselName() {
    return vesselName;
  }

  public void setVesselName(String vesselName) {
    this.vesselName = vesselName;
  }

  public String getPortLetterNumber() {
    return portLetterNumber;
  }

  public void setPortLetterNumber(String portLetterNumber) {
    this.portLetterNumber = portLetterNumber;
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

  public String getBeaconLocation() {
    return beaconLocation;
  }

  public void setBeaconLocation(String beaconLocation) {
    this.beaconLocation = beaconLocation;
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

  public String getMoreDetails() {
    return moreDetails;
  }

  public void setMoreDetails(String moreDetails) {
    this.moreDetails = moreDetails;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
