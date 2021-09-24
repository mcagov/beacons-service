package uk.gov.mca.beacons.api.controllers;

import java.util.UUID;
import java.util.function.Function;
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
class BeaconSearchRestRepositoryIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  private TestSeeder testSeeder = new TestSeeder(webTestClient);

  @Test
  void givenAccountHolderHasClaimedLegacyBeacon_ThenItShouldBeReturnedAsANormalRegistrationNotALegacyBeacon()
    throws Exception {
    // Setup
    final var email = UUID.randomUUID().toString() + "@test.com";
    final var legacyBeaconHexId = "1D0EA08C52FFBFF";
    final var legacyBeaconResponseBody = testSeeder.seedLegacyBeaconWithEmailAndHexId(
      email,
      legacyBeaconHexId
    );
    final var legacyBeaconId = testSeeder.getLegacyBeaconId(
      legacyBeaconResponseBody
    );
    final var createAccountResponseBody = testSeeder.seedAccountHolderWithEmail(
      email
    );
    final var accountHolderId = testSeeder.getAccountHolderId(
      createAccountResponseBody
    );

    final Object requestBody = testSeeder
      .registrationFixtureToMap(
        testSeeder
          .readFile("src/test/resources/fixtures/registrations.json")
          .replace("replace-with-test-account-holder-id", accountHolderId)
      )
      .get(
        RegistrationsControllerIntegrationTest.RegistrationUseCase.SINGLE_BEACON
      );

    webTestClient
      .post()
      .uri("/registrations/register")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(requestBody)
      .exchange()
      .expectStatus()
      .isCreated();

    webTestClient
      .get()
      .uri("/legacy-beacon/" + legacyBeaconId)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .jsonPath("$.data.id")
      .isEqualTo(legacyBeaconId)
      .jsonPath("$.data.attributes.event[0].type")
      .isEqualTo("claim");
  }

  private void createLegacyBeacon(Function<String, String> mapRequestObject)
    throws Exception {
    final var createLegacyBeaconRequest = mapRequestObject.apply(
      testSeeder.readFile(
        "src/test/resources/fixtures/createLegacyBeaconRequest.json"
      )
    );

    webTestClient
      .post()
      .uri("/migrate/legacy-beacon")
      .bodyValue(createLegacyBeaconRequest)
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated();
  }

  @Nested
  class GetBeaconSearchResults {

    private static final String FIND_ALL_URI = "/beacon-search/search/find-all";

    @Test
    void shouldFindTheCreatedLegacyBeacon() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      testSeeder.seedLegacyBeaconWithEmailAndHexId(
        "ownerbeacon@beacons.com",
        randomHexId
      );

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
        .isEqualTo("2004-10-13T00:00:00Z")
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
      testSeeder.seedLegacyBeaconWithEmailAndHexId(
        "ownerbeacon@beacons.com",
        randomHexId
      );

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
      testSeeder.seedLegacyBeaconWithEmailAndHexId(
        "ownerbeacon@beacons.com",
        randomHexId
      );

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
      testSeeder.seedBeacon(
        request -> request.replace("1D0EA08C52FFBFF", randomHexId)
      );

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
      testSeeder.seedBeacon(
        request -> request.replace("1D0EA08C52FFBFF", randomHexId)
      );

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
      "/beacon-search/search/find-all-by-account-holder-and-email";

    @Test
    void shouldNotFindAnyBeaconsIfEmptyQueryParamsSubmitted() throws Exception {
      testSeeder.seedBeacon(
        request -> request.replace("\"account-holder-id-placeholder\"", "null")
      );

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_BY_ACCOUNT_HOLDER)
              .queryParam("email", "")
              .queryParam("accountHolderId", "")
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beaconSearch.length()")
        .isEqualTo(0);
    }

    @Test
    void shouldFindTheLegacyBeaconByEmail() throws Exception {
      final var randomEmailAddress = UUID.randomUUID().toString();
      createLegacyBeacon(
        request ->
          request.replace("ownerbeacon@beacons.com", randomEmailAddress)
      );

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

    @Test
    void shouldFindTheBeaconByAccountHolderId() throws Exception {
      final var accountHolderId = testSeeder.getAccountHolderId(
        testSeeder.seedAccountHolderWithAuthId(UUID.randomUUID().toString())
      );
      testSeeder.seedBeacon(
        request ->
          request.replace("account-holder-id-placeholder", accountHolderId)
      );

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_BY_ACCOUNT_HOLDER)
              .queryParam("email", "")
              .queryParam("accountHolderId", accountHolderId)
              .build()
        )
        .exchange()
        .expectBody()
        .jsonPath("_embedded.beaconSearch.length()")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].accountHolderId")
        .isEqualTo(accountHolderId)
        .jsonPath("_embedded.beaconSearch[0].ownerEmail")
        .isEqualTo("nelson@royalnavy.mod.uk");
    }
  }
}
