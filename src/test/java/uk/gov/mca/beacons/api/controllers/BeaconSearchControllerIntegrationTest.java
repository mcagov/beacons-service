package uk.gov.mca.beacons.api.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
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

    private static final String FIND_ALL_URI = "/beacon-search/search/find-all";

    @Test
    void givenAValidRequest_shouldReturnAHttp200() {
      webTestClient
        .get()
        .uri(FIND_ALL_URI)
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
    }

    @Test
    void shouldFindTheCreatedLegacyBeacon() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      createLegacyBeacon(randomHexId);

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", randomHexId)
              .queryParam("status", "")
              .queryParam("uses", "")
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beacon-search[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("_embedded.beacon-search[0].lastModifiedDate")
        .isEqualTo("2004-10-13T00:00:00")
        .jsonPath("_embedded.beacon-search[0].beaconStatus")
        .isEqualTo("MIGRATED")
        .jsonPath("_embedded.beacon-search[0].ownerName")
        .isEqualTo("Mr Beacon")
        .jsonPath("_embedded.beacon-search[0].useActivities")
        .isEqualTo("Maritime, Maritime")
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }

    @Test
    void shouldFindTheLegacyBeaconByHexIdIgnoringCase() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      createLegacyBeacon(randomHexId);

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", randomHexId.toUpperCase())
              .queryParam("status", "")
              .queryParam("uses", "")
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beacon-search[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }

    @Test
    void shouldFindTheLegacyBeaconByHexIdStatusAndUses() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      createLegacyBeacon(randomHexId);

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", randomHexId)
              .queryParam("status", "migrated")
              .queryParam("uses", "maritime")
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beacon-search[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }

    @Test
    void shouldFindTheCreatedBeacon() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      createBeacon(randomHexId);

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", randomHexId)
              .queryParam("status", "")
              .queryParam("uses", "")
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beacon-search[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("_embedded.beacon-search[0].lastModifiedDate")
        .isNotEmpty()
        .jsonPath("_embedded.beacon-search[0].beaconStatus")
        .isEqualTo("NEW")
        .jsonPath("_embedded.beacon-search[0].ownerName")
        .isEqualTo("Vice-Admiral Horatio Nelson, 1st Viscount Nelson")
        .jsonPath("_embedded.beacon-search[0].useActivities")
        .isEqualTo("SAILING")
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }

    @Test
    void shouldFindTheCreatedBeaconByHexIdStatusAndUses() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      createBeacon(randomHexId);

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", randomHexId)
              .queryParam("status", "new")
              .queryParam("uses", "sail")
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beacon-search[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }
  }

  private String readFile(String filePath) throws Exception {
    return Files.readString(Paths.get(filePath));
  }

  private void createLegacyBeacon(String hexId) throws Exception {
    final var createLegacyBeaconRequest = readFile(
      "src/test/resources/fixtures/createLegacyBeaconRequest.json"
    )
      .replace("9D0E1D1B8C00001", hexId);

    webTestClient
      .post()
      .uri("/migrate/legacy-beacon")
      .bodyValue(createLegacyBeaconRequest)
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated();
  }

  private void createBeacon(String hexId) throws Exception {
    final var createBeaconRequest = readFile(
      "src/test/resources/fixtures/createBeaconRequest.json"
    )
      .replace("1D0EA08C52FFBFF", hexId)
      .replace("\"account-holder-id-placeholder\"", "null");

    webTestClient
      .post()
      .uri("/registrations/register")
      .body(BodyInserters.fromValue(createBeaconRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated();
  }
}
