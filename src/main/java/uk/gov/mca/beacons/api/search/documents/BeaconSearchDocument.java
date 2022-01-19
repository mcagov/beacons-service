package uk.gov.mca.beacons.api.search.documents;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import uk.gov.mca.beacons.api.search.documents.nested.NestedBeaconOwner;
import uk.gov.mca.beacons.api.search.documents.nested.NestedBeaconUse;

@ConditionalOnProperty(
  prefix = "opensearch",
  name = "enabled",
  havingValue = "true"
)
@Getter
@Setter
@Document(indexName = "beacon_search")
public class BeaconSearchDocument {

  @Id
  private UUID id;

  @Field
  private String hexId;

  @Field
  private String beaconStatus;

  @Field(type = FieldType.Date)
  private OffsetDateTime createdAt;

  @Field(type = FieldType.Date)
  private OffsetDateTime lastModified;

  @Field
  private String manufacturerSerialNumber;

  @Field
  private String cospasSarsatNumber;

  @Field(type = FieldType.Nested)
  private NestedBeaconOwner beaconOwner;

  @Field(type = FieldType.Nested)
  private List<NestedBeaconUse> beaconUses;
}
