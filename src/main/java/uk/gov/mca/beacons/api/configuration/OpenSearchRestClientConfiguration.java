package uk.gov.mca.beacons.api.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

@ConditionalOnProperty(
  prefix = "opensearch",
  name = "enabled",
  havingValue = "true"
)
@Configuration
@EnableElasticsearchRepositories(
  basePackages = "uk.gov.mca.beacons.api.search.repositories"
)
@ComponentScan(basePackages = { "uk.gov.mca.beacons.api.search" })
public class OpenSearchRestClientConfiguration
  extends AbstractElasticsearchConfiguration {

  @Value("${opensearch.host}")
  private String host;

  @Value("${opensearch.port}")
  private int port;

  @Bean
  @NonNull
  @Override
  @ConditionalOnProperty(
    prefix = "opensearch",
    name = "enabled",
    havingValue = "true"
  )
  public RestHighLevelClient elasticsearchClient() {
    final ClientConfiguration clientConfiguration = ClientConfiguration
      .builder()
      .connectedTo(host + ":" + port)
      .build();

    return RestClients.create(clientConfiguration).rest();
  }
}
