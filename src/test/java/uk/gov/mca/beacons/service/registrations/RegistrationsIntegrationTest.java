package uk.gov.mca.beacons.service.registrations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RegistrationsIntegrationTest {

  private static final String REGISTRATION_ENDPOINT = "/register";

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void contextLoads() {}

  @Test
  void givenNewValidRegistration_whenPosted_thenStatus201() throws IOException {
    String validRegistrationRequestBody = importFromFile(
      "src/test/resources/validRegistration.json"
    );

    webTestClient
      .post()
      .uri(REGISTRATION_ENDPOINT)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(validRegistrationRequestBody)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectHeader()
      .valueEquals("Content-Type", "application/json")
      .expectBody()
      .jsonPath("id")
      .isNotEmpty();
  }

  private String importFromFile(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)));
  }
}