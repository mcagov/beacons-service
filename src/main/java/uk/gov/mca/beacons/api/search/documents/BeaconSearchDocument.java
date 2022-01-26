package uk.gov.mca.beacons.api.search.documents;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import uk.gov.mca.beacons.api.beacon.domain.Beacon;
import uk.gov.mca.beacons.api.beaconowner.domain.BeaconOwner;
import uk.gov.mca.beacons.api.beaconuse.domain.BeaconUse;
import uk.gov.mca.beacons.api.search.documents.nested.NestedBeaconOwner;
import uk.gov.mca.beacons.api.search.documents.nested.NestedBeaconUse;

@Getter
@Setter
@Document(indexName = "beacon_search")
public class BeaconSearchDocument {

  public BeaconSearchDocument() {}

  public BeaconSearchDocument(
    Beacon beacon,
    List<BeaconUse> beaconUses,
    BeaconOwner beaconOwner
  ) {
    this.id = Objects.requireNonNull(beacon.getId()).unwrap();
    this.hexId = beacon.getHexId();
    this.beaconStatus = beacon.getBeaconStatus().toString();
    this.createdDate = beacon.getCreatedDate();
    this.lastModifiedDate = beacon.getLastModifiedDate();
    this.manufacturerSerialNumber = beacon.getManufacturerSerialNumber();
    this.beaconOwner = new NestedBeaconOwner(beaconOwner);
    this.beaconUses =
      beaconUses
        .stream()
        .map(NestedBeaconUse::new)
        .collect(Collectors.toList());
  }

  @Id
  private UUID id;

  @Field
  private String hexId;

  @Field
  private String beaconStatus;

  @Field(type = FieldType.Date)
  private OffsetDateTime createdDate;

  @Field(type = FieldType.Date)
  private OffsetDateTime lastModifiedDate;

  @Field
  private String manufacturerSerialNumber;

  @Field
  private String cospasSarsatNumber;

  @Field(type = FieldType.Nested)
  private NestedBeaconOwner beaconOwner;

  @Field(type = FieldType.Nested)
  private List<NestedBeaconUse> beaconUses;
}
