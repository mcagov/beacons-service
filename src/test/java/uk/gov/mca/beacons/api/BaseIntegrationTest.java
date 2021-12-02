package uk.gov.mca.beacons.api;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

  static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(
    DockerImageName.parse("postgres:12")
  )
    .withDatabaseName("beacons")
    .withUsername("beacons_service")
    .withPassword("password");

  static final ElasticsearchContainer OPENSEARCH_CONTAINER = new ElasticsearchContainer(
    DockerImageName
      .parse("opensearchproject/opensearch:1.1.0")
      .asCompatibleSubstituteFor(
        "docker.elastic.co/elasticsearch/elasticsearch"
      )
  )
    .withEnv(
      Map.of("plugins.security.disabled", "true", "network.host", "0.0.0.0")
    )
    .withExposedPorts(9200);

  static {
    POSTGRE_SQL_CONTAINER.start();
    OPENSEARCH_CONTAINER.start();
  }

  @DynamicPropertySource
  static void datasourceConfig(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
    registry.add(
      "spring.datasource.password",
      POSTGRE_SQL_CONTAINER::getPassword
    );
    registry.add(
      "spring.datasource.username",
      POSTGRE_SQL_CONTAINER::getUsername
    );

    registry.add("opensearch.host", OPENSEARCH_CONTAINER::getHost);
    registry.add("opensearch.port", OPENSEARCH_CONTAINER::getFirstMappedPort);
  }

  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void cleanDatabase() {
    JdbcTestUtils.deleteFromTables(
      jdbcTemplate,
      "account_holder",
      "beacon",
      "beacon_use",
      "legacy_beacon",
      "legacy_beacon_claim_event",
      "note",
      "person"
    );
  }
}
