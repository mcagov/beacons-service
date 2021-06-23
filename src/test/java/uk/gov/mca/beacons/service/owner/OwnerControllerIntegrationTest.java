package uk.gov.mca.beacons.service.owner;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class OwnerControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void shouldCreateTheOwner() throws Exception {
    final String createOwnerRequest = new String(
      Files.readAllBytes(
        Paths.get("src/test/resources/fixtures/createOwnerRequest.json")
      )
    );
    final String createOwnerResponse = new String(
      Files.readAllBytes(
        Paths.get("src/test/resources/fixtures/createOwnerResponse.json")
      )
    );

    webTestClient
      .post()
      .uri("/owner")
      .bodyValue(createOwnerRequest)
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody()
      .json(createOwnerResponse)
      .jsonPath("$.data.id")
      .isNotEmpty();
  }
}
