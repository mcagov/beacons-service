package uk.gov.mca.beacons.api.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
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
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconSearchRestRepositoryIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Nested
  class GetBeaconSearchResults {

    private static final String FIND_ALL_URI =
      "/spring-api/beacon-search/search/find-allv2";

    @Test
    void v1ShouldWork() throws Exception {
      final var randomHexId = UUID.randomUUID().toString();
      createLegacyBeacon(randomHexId);

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path("/spring-api/beacon-search/search/find-all")
              .queryParam("term", randomHexId)
              .queryParam("status", "")
              .queryParam("uses", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
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
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
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
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
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
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }

    @Test
    void shouldFindTheCreatedLegacyBeaconByManufacturerSerialNumber()
      throws Exception {
      final var manufacturerSerialNumber = UUID.randomUUID().toString();
      final var legacyBeaconHexId = UUID.randomUUID().toString();
      createLegacyBeaconWithManufacturerSerialNumber(
        legacyBeaconHexId,
        manufacturerSerialNumber
      );

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", manufacturerSerialNumber)
              .queryParam("status", "")
              .queryParam("uses", "")
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("page.totalElements")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(legacyBeaconHexId);
    }

    @Test
    void shouldFindTheCreatedLegacyBeaconByCospasSarsatNumber()
      throws Exception {
      var legacyBeaconFixtureCospasSarsatNumberValue = 476899;
      var pseudoUniqueLegacyBeaconCospasSarsatNumber = new Random()
        .nextInt(Integer.MAX_VALUE);
      createLegacyBeacon(
        request ->
          request.replace(
            Integer.toString(legacyBeaconFixtureCospasSarsatNumberValue),
            Integer.toString(pseudoUniqueLegacyBeaconCospasSarsatNumber)
          )
      );

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", pseudoUniqueLegacyBeaconCospasSarsatNumber)
              .queryParam("status", "")
              .queryParam("uses", "")
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("page.totalElements")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].cospasSarsatNumber")
        .isEqualTo(pseudoUniqueLegacyBeaconCospasSarsatNumber);
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
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
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
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(randomHexId)
        .jsonPath("page.totalElements")
        .isEqualTo(1);
    }

    @Test
    void shouldFindTheCreatedBeaconByManufacturerSerialNumber()
      throws Exception {
      var uniqueBeaconManufacturerSerialNumber = UUID.randomUUID().toString();
      createBeacon(
        request ->
          request
            .replace(
              "manufacturer-serial-number-placeholder",
              uniqueBeaconManufacturerSerialNumber
            )
            .replace("\"account-holder-id-placeholder\"", "null")
      );

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term", uniqueBeaconManufacturerSerialNumber)
              .queryParam("status", "")
              .queryParam("hexId", "")
              .queryParam("ownerName", "")
              .queryParam("cospasSarsatNumber", "")
              .queryParam("manufacturerSerialNumber", "")
              .queryParam("uses", "")
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("page.totalElements")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].manufacturerSerialNumber")
        .isEqualTo(uniqueBeaconManufacturerSerialNumber);
    }

    @Test
    void shouldFindTheCreatedLegacyBeaconWithAllFiltersSet() throws Exception {
      var legacyBeaconFixtureHexId = "9D0E1D1B8C00001";
      var legacyBeaconFixtureOwnerName = "Mr Beacon";
      var legacyBeaconFixtureCospasSarsatNumberValue = 476899;
      var legacyBeaconFixtureManufacturerSerialNumber =
        "manufacturer_serial_number_value";
      var legacyBeaconFixtureSerialNumber = 1763;

      var random = new Random();
      var uniqueLegacyBeaconHexId = UUID.randomUUID().toString();
      var uniqueLegacyBeaconOwnerName = UUID.randomUUID().toString();
      var pseudoUniqueLegacyBeaconCospasSarsatNumber = random.nextInt(
        Integer.MAX_VALUE
      );
      var uniqueLegacyBeaconManufacturerSerialNumber = UUID
        .randomUUID()
        .toString();

      createLegacyBeacon(
        request ->
          request
            .replace(legacyBeaconFixtureHexId, uniqueLegacyBeaconHexId)
            .replace(legacyBeaconFixtureOwnerName, uniqueLegacyBeaconOwnerName)
            .replace(
              Integer.toString(legacyBeaconFixtureCospasSarsatNumberValue),
              Integer.toString(pseudoUniqueLegacyBeaconCospasSarsatNumber)
            )
            .replace(
              legacyBeaconFixtureManufacturerSerialNumber,
              uniqueLegacyBeaconManufacturerSerialNumber
            )
      );

      webTestClient
        .get()
        .uri(
          uriBuilder ->
            uriBuilder
              .path(FIND_ALL_URI)
              .queryParam("term")
              .queryParam("status", "MIGRATED")
              .queryParam("uses", "MARITIME")
              .queryParam("hexId", uniqueLegacyBeaconHexId)
              .queryParam("ownerName", uniqueLegacyBeaconOwnerName)
              .queryParam(
                "cospasSarsatNumber",
                pseudoUniqueLegacyBeaconCospasSarsatNumber
              )
              .queryParam(
                "manufacturerSerialNumber",
                uniqueLegacyBeaconManufacturerSerialNumber
              )
              .build()
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("page.totalElements")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(uniqueLegacyBeaconHexId);
    }
  }

  @Nested
  class GetBeaconSearchResultsForAccountHolder {

    private static final String FIND_BY_ACCOUNT_HOLDER =
      "/spring-api/beacon-search/search/find-all-by-account-holder-and-email";

    @Test
    void shouldNotFindAnyBeaconsIfEmptyQueryParamsSubmitted() throws Exception {
      createBeacon(
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
        .expectStatus()
        .isOk()
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
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("_embedded.beaconSearch.length()")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].ownerEmail")
        .isEqualTo(randomEmailAddress);
    }

    @Test
    void shouldFindTheBeaconByAccountHolderId() throws Exception {
      final var accountHolderId = createAccountHolder(
        UUID.randomUUID().toString()
      );
      createBeacon(
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
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("_embedded.beaconSearch.length()")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].accountHolderId")
        .isEqualTo(accountHolderId)
        .jsonPath("_embedded.beaconSearch[0].ownerEmail")
        .isEqualTo("nelson@royalnavy.mod.uk");
    }
  }

  private String readFile(String filePath) throws Exception {
    return Files.readString(Paths.get(filePath));
  }

  private void createLegacyBeacon(Function<String, String> mapRequestObject)
    throws Exception {
    final var createLegacyBeaconRequest = mapRequestObject.apply(
      readFile("src/test/resources/fixtures/createLegacyBeaconRequest.json")
    );

    webTestClient
      .post()
      .uri("/spring-api/migrate/legacy-beacon")
      .bodyValue(createLegacyBeaconRequest)
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated();
  }

  private void createLegacyBeacon(String hexId) throws Exception {
    createLegacyBeacon(request -> request.replace("9D0E1D1B8C00001", hexId));
  }

  private void createLegacyBeaconWithManufacturerSerialNumber(
    String hexId,
    String manufacturerSerialNumber
  ) throws Exception {
    createLegacyBeacon(
      request ->
        request
          .replace("9D0E1D1B8C00001", hexId)
          .replace("manufacturer_serial_number_value", manufacturerSerialNumber)
    );
  }

  private void createBeacon(String hexId) throws Exception {
    createBeacon(
      request ->
        request
          .replace("1D0EA08C52FFBFF", hexId)
          .replace("\"account-holder-id-placeholder\"", "null")
    );
  }

  private void createBeacon(Function<String, String> mapRequestObject)
    throws Exception {
    final var createBeaconRequest = mapRequestObject.apply(
      readFile("src/test/resources/fixtures/createBeaconRequest.json")
    );

    webTestClient
      .post()
      .uri("/spring-api/registrations/register")
      .body(BodyInserters.fromValue(createBeaconRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated();
  }

  private String createAccountHolder(String testAuthId) throws Exception {
    final String newAccountHolderRequest = readFile(
      "src/test/resources/fixtures/createAccountHolderRequest.json"
    )
      .replace("replace-with-test-auth-id", testAuthId);

    return webTestClient
      .post()
      .uri("/spring-api/account-holder")
      .body(BodyInserters.fromValue(newAccountHolderRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectBody(ObjectNode.class)
      .returnResult()
      .getResponseBody()
      .get("data")
      .get("id")
      .textValue();
  }
}
