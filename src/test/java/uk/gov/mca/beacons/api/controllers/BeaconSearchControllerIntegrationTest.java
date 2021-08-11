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
    void shouldFindTheCreatedLegacyBeacon() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      final var createLegacyBeaconRequest = readFile(
        "src/test/resources/fixtures/createLegacyBeaconRequest.json"
      )
        .replace("9D0E1D1B8C00001", randomHexId);

      webTestClient
        .post()
        .uri("/migrate/legacy-beacon")
        .bodyValue(createLegacyBeaconRequest)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated();

      final String uri = "/beacon-search/search/find-all";
      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder.path(uri).queryParam("term", randomHexId).build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beacon-search[0].hexId")
        .isEqualTo(randomHexId);
    }

    @Test
    void shouldFindTheCreatedBeacon() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      final var createBeaconRequest = readFile(
        "src/test/resources/fixtures/createBeaconRequest.json"
      )
        .replace("1D0EA08C52FFBFF", randomHexId);

      webTestClient
        .post()
        .uri("/registrations/register")
        .bodyValue(createBeaconRequest)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated();

      final String uri = "/beacon-search/search/find-all";
      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder.path(uri).queryParam("term", randomHexId).build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beacon-search[0].hexId")
        .isEqualTo(randomHexId);
    }
  }

  private String readFile(String filePath) throws Exception {
    return Files.readString(Path.of(filePath));
  }
}
