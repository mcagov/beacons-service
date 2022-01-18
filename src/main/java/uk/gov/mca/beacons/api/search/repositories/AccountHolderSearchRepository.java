package uk.gov.mca.beacons.api.search.repositories;

import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import uk.gov.mca.beacons.api.search.documents.AccountHolderDocument;

@ConditionalOnProperty(
  prefix = "opensearch",
  name = "enabled",
  havingValue = "true"
)
public interface AccountHolderSearchRepository
  extends ElasticsearchRepository<AccountHolderDocument, UUID> {}
