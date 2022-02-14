package uk.gov.mca.beacons.api.search.documents.nested;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import uk.gov.mca.beacons.api.beaconuse.domain.BeaconUse;
import uk.gov.mca.beacons.api.legacybeacon.domain.LegacyUse;

@Getter
@Setter
public class NestedBeaconUse {

  public NestedBeaconUse() {}

  public NestedBeaconUse(BeaconUse beaconUse) {
    this.environment = beaconUse.getEnvironment().toString().toUpperCase();
    this.activity = beaconUse.getActivity().toString().toUpperCase();
    this.purpose = beaconUse.getPurpose().toString().toUpperCase();
    this.vesselName = beaconUse.getVesselName();
    this.callSign = beaconUse.getCallSign();
    this.aircraftRegistrationMark = beaconUse.getRegistrationMark();
  }

  public NestedBeaconUse(LegacyUse legacyUse) {
    this.environment = legacyUse.getEnvironment();
    this.activity = legacyUse.getActivity();
    this.purpose = legacyUse.getPurpose();
    this.vesselName = legacyUse.getVesselName();
    this.callSign = legacyUse.getCallSign();
    this.aircraftRegistrationMark = legacyUse.getAircraftRegistrationMark();
  }

  @Field(type = FieldType.Keyword)
  private String environment;

  @Field(type = FieldType.Keyword)
  private String activity;

  @Field(type = FieldType.Keyword)
  private String purpose;

  @Field(type = FieldType.Keyword)
  private String vesselName;

  @Field(type = FieldType.Keyword)
  private String callSign;

  @Field(type = FieldType.Keyword)
  private String aircraftRegistrationMark;
}
