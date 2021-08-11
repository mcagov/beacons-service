package uk.gov.mca.beacons.api.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconSearchControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Nested
  class GetBeaconSearchResults {

    @Test
    void givenAValidRequest_shouldReturnAHttp200() {
      final String uri = "/beacon-search/search/find-all";

      webTestClient.get().uri(uri).exchange().expectStatus().is2xxSuccessful();
    }

    @Test
    void shouldReturnTheCreatedLegacyBeacon() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      final var createLegacyBeaconRequest = readFile(
        "src/test/resources/fixtures/createLegacyBeaconRequest.json"
      )
        .replace("hexId", randomHexId);

      webTestClient
        .post()
        .uri("/migrate/legacy-beacon")
        .bodyValue(createLegacyBeaconRequest)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated();

      final String uri = "/beacon-search/search/find-all?q=" + randomHexId;
      webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beaconSearches[0].hexId")
        .isEqualTo(randomHexId);
    }
  }

  private String readFile(String filePath) throws Exception {
    return Files.readString(Path.of(filePath));
  }
}
