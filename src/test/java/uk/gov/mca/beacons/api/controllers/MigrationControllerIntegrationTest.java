package uk.gov.mca.beacons.api.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MigrationControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private JdbcTemplate jdbcTemplate;

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

      webTestClient
        .get()
        .uri("/migrate/delete-all-legacy-beacons")
        .exchange()
        .expectStatus()
        .isOk();

      final var count = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM legacy_beacon",
        Integer.class
      );
      assertThat(count, is(0));
    }

    @Test
    void shouldRespondWithOKStatusIfLegacyBeaconsDeleted() {
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
