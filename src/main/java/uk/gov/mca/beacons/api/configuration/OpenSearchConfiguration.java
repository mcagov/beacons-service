package uk.gov.mca.beacons.api.configuration;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.base.RestClientTransport;
import org.opensearch.client.base.Transport;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfiguration {

  @Bean
  public OpenSearchClient client() {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(
      AuthScope.ANY,
      new UsernamePasswordCredentials("admin", "admin")
    );
    RestClient restClient = RestClient
      .builder(new HttpHost("localhost", 9200, "https"))
      .setHttpClientConfigCallback(
        new RestClientBuilder.HttpClientConfigCallback() {
          @Override
          public HttpAsyncClientBuilder customizeHttpClient(
            HttpAsyncClientBuilder httpClientBuilder
          ) {
            return httpClientBuilder.setDefaultCredentialsProvider(
              credentialsProvider
            );
          }
        }
      )
      .build();
    Transport transport = new RestClientTransport(
      restClient,
      new JacksonJsonpMapper()
    );
    OpenSearchClient client = new OpenSearchClient(transport);
    return client;
  }
}
