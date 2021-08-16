package uk.gov.mca.beacons.api.controllers;

import static org.hamcrest.Matchers.hasItems;

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

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", randomHexId)
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
        .isEqualTo("Maritime, Maritime");
    }

    @Test
    void shouldFindTheCreatedBeacon() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      final var createBeaconRequest = readFile(
        "src/test/resources/fixtures/createBeaconRequest.json"
      )
        .replace("1D0EA08C52FFBFF", randomHexId)
        .replace("\"account-holder-id-placeholder\"", "null");

      webTestClient
        .post()
        .uri("/registrations/register")
        .body(BodyInserters.fromValue(createBeaconRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated();

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", randomHexId)
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
        .isEqualTo("SAILING");
    }
  }

  private String readFile(String filePath) throws Exception {
    return Files.readString(Paths.get(filePath));
  }
}