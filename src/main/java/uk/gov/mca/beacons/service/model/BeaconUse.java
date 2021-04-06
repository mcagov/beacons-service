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

  private boolean mainUse;

  private UUID beaconPersonId;

  private String beaconPosition;

  @CreatedDate
  private LocalDateTime createdDate;
}
