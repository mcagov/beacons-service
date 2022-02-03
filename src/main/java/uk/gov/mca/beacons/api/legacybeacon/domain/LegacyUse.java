package uk.gov.mca.beacons.api.legacybeacon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.*;
import uk.gov.mca.beacons.api.shared.domain.base.ValueObject;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegacyUse implements ValueObject, Serializable {

  private String note;
  private String notes;
  private String isMain;
  private String landUse;
  private String rigName;
  private String useType;
  private String callSign;
  private String homePort;
  private String position;
  private String tripInfo;
  private String areaOfUse;
  private String beaconNsn;
  private String imoNumber;
  private Integer fkBeaconId;
  private Integer maxPersons;
  private Integer mmsiNumber;
  private Integer versioning;
  private String vesselName;
  private String vesselType;
  private String createdDate;
  private String aircraftType;
  private Integer createUserId;
  private String hullIdNumber;
  private String rssSsrNumber;
  private Integer updateUserId;
  private String cg66RefNumber;
  private String pennantNumber;
  private String beaconPosition;
  private String communications;
  private String officialNumber;
  private Integer pkBeaconUsesId;
  private String aodSerialNumber;
  private String bit24AddressHex;
  private String beaconPartNumber;
  private String fishingVesselPln;
  private String lastModifiedDate;
  private String principalAirport;
  private String localManagementId;
  private String survivalCraftType;
  private String aircraftDescription;
  private String aircraftRegistrationMark;

  public String getEnvironment() {
    return getUseType();
  }
}
