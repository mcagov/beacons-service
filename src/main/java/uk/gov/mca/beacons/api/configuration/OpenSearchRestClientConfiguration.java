package uk.gov.mca.beacons.api.configuration;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.lang.NonNull;

@Configuration
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
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(
      AuthScope.ANY,
      new UsernamePasswordCredentials(username, password)
    );

    final ClientConfiguration clientConfiguration = ClientConfiguration
      .builder()
      .connectedTo("localhost:9200")
      .withBasicAuth("admin", "admin")
      .build();

    return RestClients.create(clientConfiguration).rest();
  }
}
