package uk.gov.mca.beacons.api.db;

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
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uk.gov.mca.beacons.api.entities.Activity;
import uk.gov.mca.beacons.api.entities.Environment;
import uk.gov.mca.beacons.api.entities.Purpose;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
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

  @NotNull
  private Boolean mainUse;

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
}
