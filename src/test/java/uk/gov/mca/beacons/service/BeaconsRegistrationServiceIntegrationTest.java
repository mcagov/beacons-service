package uk.gov.mca.beacons.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconsRegistrationServiceIntegrationTest {

  private static final String ACTUATOR_HEALTH_ENDPOINT = "/actuator/health";

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void contextLoads() {}

  @Test
  void actuatorEndpointShouldReturnUp() {
    webTestClient
      .get()
      .uri(ACTUATOR_HEALTH_ENDPOINT)
      .exchange()
      .expectStatus()
      .is2xxSuccessful()
      .expectBody()
      .json("{\"status\": \"UP\"}");
  }
}
