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
class RegistrationsControllerIntegrationTest {

  private static final String REGISTRATION_ENDPOINT = "/registrations/register";

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void givenNewValidRegistration_whenPosted_thenStatus201() throws IOException {
    final String validRegistrationRequestBody = importFromFile(
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
      .valueEquals("Content-Type", "application/json");
  }

  private String importFromFile(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)));
  }
}
