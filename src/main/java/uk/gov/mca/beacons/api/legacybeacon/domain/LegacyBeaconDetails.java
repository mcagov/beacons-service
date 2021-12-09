package uk.gov.mca.beacons.api.legacybeacon.domain;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;
import uk.gov.mca.beacons.api.shared.domain.base.ValueObject;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegacyBeaconDetails implements ValueObject, Serializable {

  private String note;
  private String hexId;
  private String model;
  private String coding;
  private String protocol;
  private String isPending;
  private String beaconType;
  private String isArchived;
  private Integer pkBeaconId;
  private String statusCode;
  private Integer versioning;

  @NotBlank
  private String createdDate;

  @NotBlank
  private String lastModifiedDate;

  private String departRefId;
  private String isWithdrawn;
  private Integer createUserId;
  private String manufacturer;
  private Integer serialNumber;
  private Integer updateUserId;
  private String lastServiceDate;
  private String withdrawnReason;
  private String batteryExpiryDate;
  private Integer cospasSarsatNumber;
  private String firstRegistrationDate;
  private String manufacturerSerialNumber;
}
