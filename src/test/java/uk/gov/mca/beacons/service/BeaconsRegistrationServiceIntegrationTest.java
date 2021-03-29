package uk.gov.mca.beacons.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconsRegistrationServiceIntegrationTest {

  private static final String ACTUATOR_ENDPOINT = "/actuator";
  private static final String ACTUATOR_HEALTH_ENDPOINT =
    ACTUATOR_ENDPOINT + "/health";
  private static final String ACTUATOR_INFO_ENDPOINT =
    ACTUATOR_ENDPOINT + "/info";

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void contextLoads() {}

  @Test
  void actuatorEndpointShouldReturnUp() {
    makeGet(ACTUATOR_HEALTH_ENDPOINT).expectBody().json("{\"status\": \"UP\"}");
  }

  @Test
  void actuatorGitInfoEndpoint() {
    makeGet(ACTUATOR_INFO_ENDPOINT).expectBody().jsonPath("$.gitt").exists();
  }

  private WebTestClient.ResponseSpec makeGet(String url) {
    return webTestClient
      .get()
      .uri(url)
      .exchange()
      .expectStatus()
      .is2xxSuccessful();
  }
}
