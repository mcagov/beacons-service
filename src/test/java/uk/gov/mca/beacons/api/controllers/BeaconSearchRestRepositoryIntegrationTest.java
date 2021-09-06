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
import uk.gov.mca.beacons.api.services.scheduled.BeaconSearchScheduler;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconSearchRestRepositoryIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private BeaconSearchScheduler scheduler;

  @Nested
  class GetBeaconSearchResults {

    private static final String FIND_ALL_URI = "/beacon-search/search/find-all";

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
        .jsonPath("_embedded.beaconSearch[0].id")
        .isNotEmpty()
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("_embedded.beaconSearch[0].lastModifiedDate")
        .isEqualTo("2004-10-13T00:00:00")
        .jsonPath("_embedded.beaconSearch[0].beaconStatus")
        .isEqualTo("MIGRATED")
        .jsonPath("_embedded.beaconSearch[0].ownerName")
        .isEqualTo("Mr Beacon")
        .jsonPath("_embedded.beaconSearch[0].useActivities")
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
        .jsonPath("_embedded.beaconSearch[0].hexId")
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
        .jsonPath("_embedded.beaconSearch[0].hexId")
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
        .jsonPath("_embedded.beaconSearch[0].id")
        .isNotEmpty()
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("_embedded.beaconSearch[0].lastModifiedDate")
        .isNotEmpty()
        .jsonPath("_embedded.beaconSearch[0].beaconStatus")
        .isEqualTo("NEW")
        .jsonPath("_embedded.beaconSearch[0].ownerName")
        .isEqualTo("Vice-Admiral Horatio Nelson, 1st Viscount Nelson")
        .jsonPath("_embedded.beaconSearch[0].useActivities")
        .isEqualTo("FISHING VESSEL, FLOATING PLATFORM")
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
              .queryParam("uses", "fishing vessel")
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }
  }

  @Nested
  class GetBeaconSearchResultsForAccountHolder {

    private static final String FIND_BY_ACCOUNT_HOLDER =
      "/beacon-search/search/find-all-by-account-holder";

    @Test
    void shouldFindTheLegacyBeaconByEmail() throws Exception {
      final var randomEmailAddress = UUID.randomUUID().toString();
      createLegacyBeacon("1D0EA08C52FFBFF", randomEmailAddress);

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_BY_ACCOUNT_HOLDER)
              .queryParam("email", randomEmailAddress)
              .queryParam("accountHolderId", UUID.randomUUID().toString())
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beaconSearch.length()")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].ownerEmail")
        .isEqualTo(randomEmailAddress);
    }
  }

  private String readFile(String filePath) throws Exception {
    return Files.readString(Paths.get(filePath));
  }

  private void createLegacyBeacon(String hexId, String email) throws Exception {
    final var createLegacyBeaconRequest = readFile(
      "src/test/resources/fixtures/createLegacyBeaconRequest.json"
    )
      .replace("9D0E1D1B8C00001", hexId)
      .replace("ownerbeacon@beacons.com", email);

    webTestClient
      .post()
      .uri("/migrate/legacy-beacon")
      .bodyValue(createLegacyBeaconRequest)
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated();

    scheduler.refreshView();
  }

  private void createLegacyBeacon(String hexId) throws Exception {
    createBeacon(hexId, "ownerbeacon@beacons.com");
  }

  private void createBeacon(String hexId) throws Exception {
    createBeacon(hexId, "nelson@royalnavy.mod.uk");
  }

  private void createBeacon(String hexId, String ownerEmail) throws Exception {
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

    scheduler.refreshView();
  }
}
