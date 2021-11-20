package uk.gov.mca.beacons.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.wimdeblauwe.testcontainers.cypress.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
  initializers = BeaconsEndToEndTests.DockerPostgreDataSourceInitializer.class
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BeaconsEndToEndTests {

  @LocalServerPort
  private int port;

  @Container
  private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>(
    "postgres:12"
  )
    .withDatabaseName("beacons")
    .withUsername("beacons_service")
    .withPassword("password");

  public static class DockerPostgreDataSourceInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(
      @NotNull ConfigurableApplicationContext applicationContext
    ) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
        applicationContext,
        "spring.datasource.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
        "spring.datasource.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
        "spring.datasource.password=" + POSTGRE_SQL_CONTAINER.getPassword()
      );
    }
  }

  @TestFactory
  List<DynamicContainer> runCypressTests()
    throws InterruptedException, IOException, TimeoutException {
    org.testcontainers.Testcontainers.exposeHostPorts(port);

    MochawesomeGatherTestResultsStrategy gradleTestResultStrategy = new MochawesomeGatherTestResultsStrategy(
      FileSystems
        .getDefault()
        .getPath(
          "build",
          "resources",
          "test",
          "e2e",
          "cypress",
          "reports",
          "mochawesome"
        )
    );

    try (
      CypressContainer container = new CypressContainer()
        .withLocalServerPort(port)
        .withGatherTestResultsStrategy(gradleTestResultStrategy)
    ) {
      container.start();
      CypressTestResults testResults = container.getTestResults();

      return convertToJUnitDynamicTests(testResults);
    }
  }

  @NotNull
  private List<DynamicContainer> convertToJUnitDynamicTests(
    CypressTestResults testResults
  ) {
    List<DynamicContainer> dynamicContainers = new ArrayList<>();
    List<CypressTestSuite> suites = testResults.getSuites();
    for (CypressTestSuite suite : suites) {
      createContainerFromSuite(dynamicContainers, suite);
    }
    return dynamicContainers;
  }

  private void createContainerFromSuite(
    List<DynamicContainer> dynamicContainers,
    CypressTestSuite suite
  ) {
    List<DynamicTest> dynamicTests = new ArrayList<>();
    for (CypressTest test : suite.getTests()) {
      dynamicTests.add(
        DynamicTest.dynamicTest(
          test.getDescription(),
          () -> assertTrue(test.isSuccess())
        )
      );
    }
    dynamicContainers.add(
      DynamicContainer.dynamicContainer(suite.getTitle(), dynamicTests)
    );
  }
}
