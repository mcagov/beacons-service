package uk.gov.mca.beacons.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LegacyBeaconControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void shouldReturnTheLegacyBeaconById() throws Exception {
    final var legacyBeaconId = createLegacyBeacon();
    final var createLegacyBeaconResponse = Files.readString(
      Paths.get("src/test/resources/fixtures/createLegacyBeaconResponse.json")
    );

    webTestClient
      .get()
      .uri("/legacy-beacon/" + legacyBeaconId)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(createLegacyBeaconResponse)
      .jsonPath("$.data.id")
      .isEqualTo(legacyBeaconId);
  }

  @Test
  void shouldReturnHttp404IfTheLegacyBeaconCannotBeFound() {
    final var legacyBeaconId = UUID.randomUUID().toString();

    webTestClient
      .get()
      .uri("/legacy-beacon/" + legacyBeaconId)
      .exchange()
      .expectStatus()
      .isNotFound();
  }

  private String createLegacyBeacon() throws Exception {
    final var createLegacyBeaconRequest = Files.readString(
      Paths.get("src/test/resources/fixtures/createLegacyBeaconRequest.json")
    );

    final var createdAccountResponse = webTestClient
      .post()
      .uri("/migrate/legacy-beacon")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createLegacyBeaconRequest)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody()
      .returnResult()
      .getResponseBody();

    return new ObjectMapper()
      .readValue(createdAccountResponse, ObjectNode.class)
      .get("data")
      .get("id")
      .textValue();
  }
}
