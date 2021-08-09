package uk.gov.mca.beacons.api.controllers;

import static org.hamcrest.Matchers.hasItems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Disabled;
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
class MigrationControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Nested
  class CreateLegacyBeacon {

    @Test
    void shouldRespondWithTheOKIfTheResourceIsCreated() throws Exception {
      final String createLegacyBeaconRequest = readFile(
        "src/test/resources/fixtures/createLegacyBeaconRequest.json"
      );

      webTestClient
        .post()
        .uri("/migrate/legacy-beacon")
        .body(BodyInserters.fromValue(createLegacyBeaconRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated();
    }

    @Test
    void shouldRespondWithTheCreatedResource() throws Exception {
      final String createLegacyBeaconRequest = readFile(
        "src/test/resources/fixtures/createLegacyBeaconRequest.json"
      );

      final String createLegacyBeaconResponse = readFile(
        "src/test/resources/fixtures/createLegacyBeaconResponse.json"
      );

      webTestClient
        .post()
        .uri("/migrate/legacy-beacon")
        .body(BodyInserters.fromValue(createLegacyBeaconRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody()
        .jsonPath("$.data.id")
        .isNotEmpty()
        .json(createLegacyBeaconResponse);
    }

    @Test
    void shouldReturnWithBadRequestAndTheValidationErrors() throws Exception {
      final String createLegacyBeaconInvalidRequest = readFile(
        "src/test/resources/fixtures/createLegacyBeaconInvalidRequest.json"
      );

      webTestClient
        .post()
        .uri("/migrate/legacy-beacon")
        .body(BodyInserters.fromValue(createLegacyBeaconInvalidRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.errors.length()")
        .isEqualTo(2)
        .jsonPath("$.errors[*].field")
        .value(hasItems("beacon[createdDate]", "beacon[lastModifiedDate]"));
    }
  }

  @Nested
  class DeleteAllLegacyBeacons {

    @Test
    @Disabled
    void shouldDeleteAllBeacons() throws Exception {
      final String createLegacyBeaconRequest = readFile(
        "src/test/resources/fixtures/createLegacyBeaconRequest.json"
      );

      webTestClient
        .post()
        .uri("/migrate/legacy-beacon")
        .body(BodyInserters.fromValue(createLegacyBeaconRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated();

      // TODO: Check that the beacon was created

      webTestClient
        .get()
        .uri("/migrate/delete-all-legacy-beacons")
        .exchange()
        .expectStatus()
        .isOk();
      // TODO: Fetch all legacy beacons and confirm no records present
    }

    @Test
    void shouldRespondWithOKStatusIfLegacyBeaconsDeleted() throws Exception {
      webTestClient
        .get()
        .uri("/migrate/delete-all-legacy-beacons")
        .exchange()
        .expectStatus()
        .isOk();
    }
  }

  private String readFile(String filePath) throws IOException {
    return Files.readString(Paths.get(filePath));
  }
}
