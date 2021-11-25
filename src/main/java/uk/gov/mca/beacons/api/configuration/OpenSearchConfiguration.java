package uk.gov.mca.beacons.api.configuration;

import java.util.HashMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.action.admin.indices.create.CreateIndexRequest;
import org.opensearch.action.admin.indices.create.CreateIndexResponse;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfiguration {

  @Value("${opensearch.credentials.user}")
  private String username;

  @Value("${opensearch.credentials.password}")
  private String password;

  @Value("${opensearch.host.name}")
  private String hostname;

  @Value("${opensearch.host.port}")
  private int port;

  @Value("${opensearch.host.scheme}")
  private String scheme;

  @Bean
  RestHighLevelClient restHighLevelClient() {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    credentialsProvider.setCredentials(
      AuthScope.ANY,
      new UsernamePasswordCredentials(username, password)
    );

    RestClientBuilder builder = RestClient
      .builder(new HttpHost(hostname, port, scheme))
      .setHttpClientConfigCallback(
        httpClientBuilder ->
          httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
      );
    return new RestHighLevelClient(builder);
  }
}
