package uk.gov.mca.beacons.api.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableElasticsearchRepositories(
  basePackages = "uk.gov.mca.beacons.api.search.repositories"
)
@ComponentScan(basePackages = { "uk.gov.mca.beacons.api.search" })
public class OpenSearchRestClientConfiguration
  extends AbstractElasticsearchConfiguration {

  @Value("${opensearch.credentials.user}")
  private String username;

  @Value("${opensearch.credentials.password}")
  private String password;

  @Bean
  @NonNull
  @Override
  public RestHighLevelClient elasticsearchClient() {
    final ClientConfiguration clientConfiguration = ClientConfiguration
      .builder()
      .connectedTo("localhost:9200")
      .build();

    return RestClients.create(clientConfiguration).rest();
  }
}
