package uk.gov.mca.beacons.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeaconsRegistrationServiceIntegrationTest {

  private static final String ACTUATOR_HEALTH_ENDPOINT = "/actuator/health";

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void contextLoads() {}

  @Test
  void actuatorEndpointShouldReturnOK() {
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
