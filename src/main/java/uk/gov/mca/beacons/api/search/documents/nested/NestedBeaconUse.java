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
    this.environment = beaconUse.getEnvironment().toString();
  }

  public NestedBeaconUse(LegacyUse legacyUse) {
    this.environment = legacyUse.getEnvironment();
  }

  @Field(type = FieldType.Keyword)
  private String environment;
}
