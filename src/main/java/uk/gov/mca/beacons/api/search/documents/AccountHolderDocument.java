package uk.gov.mca.beacons.api.search.documents;

import java.util.UUID;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Setter
@Document(indexName = "account_holder")
public class AccountHolderDocument {

  @Id
  UUID id;

  @Field
  String name;

  @Field
  String email;
}
