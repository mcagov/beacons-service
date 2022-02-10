package uk.gov.mca.beacons.api.search.documents.nested;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwner;
import uk.gov.mca.beacons.api.legacybeacon.domain.LegacyOwner;

@Getter
@Setter
public class NestedBeaconOwner {

  public NestedBeaconOwner() {}

  public NestedBeaconOwner(BeaconOwner beaconOwner) {
    this.ownerName = beaconOwner.getFullName();
  }

  public NestedBeaconOwner(LegacyOwner legacyOwner) {
    this.ownerName = legacyOwner.getOwnerName();
  }

  @Field(type = FieldType.Search_As_You_Type)
  private String ownerName;
}
