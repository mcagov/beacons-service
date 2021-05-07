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

  @Enumerated(EnumType.STRING)
  private Purpose purpose;

  @Enumerated(EnumType.STRING)
  @NotNull
  private Activity activity;

  private String otherActivity;

  private String callSign;

  private Boolean vhfRadio;

  private Boolean fixedVhfRadio;

  private String fixedVhfRadioValue;

  private Boolean portableVhfRadio;

  private String portableVhfRadioValue;

  private Boolean satelliteTelephone;

  private String satelliteTelephoneValue;

  private Boolean mobileTelephone;

  @Column(name = "mobile_telephone_1")
  private String mobileTelephone1;

  @Column(name = "mobile_telephone_2")
  private String mobileTelephone2;

  private Boolean otherCommunication;

  private String otherCommunicationValue;

  private Integer maxCapacity;

  private String vesselName;

  private String portLetterNumber;

  private String homeport;

  private String areaOfOperation;

  private String beaconLocation;

  private String imoNumber;

  private String ssrNumber;

  private String rssNumber;

  private String officialNumber;

  private String rigPlatformLocation;

  private boolean mainUse;

  private String aircraftManufacturer;

  private String principalAirport;

  private String secondaryAirport;

  private String registrationMark;

  private String hexAddress;

  private String cnOrMsnNumber;

  private Boolean dongle;

  private String beaconPosition;

  private String workingRemotelyLocation;

  private String workingRemotelyPeopleCount;

  private String windfarmLocation;

  private String windfarmPeopleCount;

  private String otherActivityLocation;

  private String otherActivityPeopleCount;

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

  public String getFixedVhfRadioValue() {
    return fixedVhfRadioValue;
  }

  public void setFixedVhfRadioValue(String fixedVhfRadioValue) {
    this.fixedVhfRadioValue = fixedVhfRadioValue;
  }

  public Boolean getPortableVhfRadio() {
    return portableVhfRadio;
  }

  public void setPortableVhfRadio(Boolean portableVhfRadio) {
    this.portableVhfRadio = portableVhfRadio;
  }

  public String getPortableVhfRadioValue() {
    return portableVhfRadioValue;
  }

  public void setPortableVhfRadioValue(String portableVhfRadioValue) {
    this.portableVhfRadioValue = portableVhfRadioValue;
  }

  public Boolean getSatelliteTelephone() {
    return satelliteTelephone;
  }

  public void setSatelliteTelephone(Boolean satelliteTelephone) {
    this.satelliteTelephone = satelliteTelephone;
  }

  public String getSatelliteTelephoneValue() {
    return satelliteTelephoneValue;
  }

  public void setSatelliteTelephoneValue(String satelliteTelephoneValue) {
    this.satelliteTelephoneValue = satelliteTelephoneValue;
  }

  public Boolean getMobileTelephone() {
    return mobileTelephone;
  }

  public void setMobileTelephone(Boolean mobileTelephone) {
    this.mobileTelephone = mobileTelephone;
  }

  public String getMobileTelephone1() {
    return mobileTelephone1;
  }

  public void setMobileTelephone1(String mobileTelephone1) {
    this.mobileTelephone1 = mobileTelephone1;
  }

  public String getMobileTelephone2() {
    return mobileTelephone2;
  }

  public void setMobileTelephone2(String mobileTelephone2) {
    this.mobileTelephone2 = mobileTelephone2;
  }

  public Boolean getOtherCommunication() {
    return otherCommunication;
  }

  public void setOtherCommunication(Boolean otherCommunication) {
    this.otherCommunication = otherCommunication;
  }

  public String getOtherCommunicationValue() {
    return otherCommunicationValue;
  }

  public void setOtherCommunicationValue(String otherCommunicationValue) {
    this.otherCommunicationValue = otherCommunicationValue;
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

  public String getImoNumber() {
    return imoNumber;
  }

  public void setImoNumber(String imoNumber) {
    this.imoNumber = imoNumber;
  }

  public String getSsrNumber() {
    return ssrNumber;
  }

  public void setSsrNumber(String ssrNumber) {
    this.ssrNumber = ssrNumber;
  }

  public String getRssNumber() {
    return rssNumber;
  }

  public void setRssNumber(String rssNumber) {
    this.rssNumber = rssNumber;
  }

  public String getOfficialNumber() {
    return officialNumber;
  }

  public void setOfficialNumber(String officialNumber) {
    this.officialNumber = officialNumber;
  }

  public String getRigPlatformLocation() {
    return rigPlatformLocation;
  }

  public void setRigPlatformLocation(String rigPlatformLocation) {
    this.rigPlatformLocation = rigPlatformLocation;
  }

  public boolean isMainUse() {
    return mainUse;
  }

  public void setMainUse(boolean mainUse) {
    this.mainUse = mainUse;
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

  public String getBeaconPosition() {
    return beaconPosition;
  }

  public void setBeaconPosition(String beaconPosition) {
    this.beaconPosition = beaconPosition;
  }

  public String getWorkingRemotelyLocation() {
    return workingRemotelyLocation;
  }

  public void setWorkingRemotelyLocation(String workingRemotelyLocation) {
    this.workingRemotelyLocation = workingRemotelyLocation;
  }

  public String getWorkingRemotelyPeopleCount() {
    return workingRemotelyPeopleCount;
  }

  public void setWorkingRemotelyPeopleCount(String workingRemotelyPeopleCount) {
    this.workingRemotelyPeopleCount = workingRemotelyPeopleCount;
  }

  public String getWindfarmLocation() {
    return windfarmLocation;
  }

  public void setWindfarmLocation(String windfarmLocation) {
    this.windfarmLocation = windfarmLocation;
  }

  public String getWindfarmPeopleCount() {
    return windfarmPeopleCount;
  }

  public void setWindfarmPeopleCount(String windfarmPeopleCount) {
    this.windfarmPeopleCount = windfarmPeopleCount;
  }

  public String getOtherActivityLocation() {
    return otherActivityLocation;
  }

  public void setOtherActivityLocation(String otherActivityLocation) {
    this.otherActivityLocation = otherActivityLocation;
  }

  public String getOtherActivityPeopleCount() {
    return otherActivityPeopleCount;
  }

  public void setOtherActivityPeopleCount(String otherActivityPeopleCount) {
    this.otherActivityPeopleCount = otherActivityPeopleCount;
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
