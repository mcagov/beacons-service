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

  @Test
  void shouldReturnHttp200IfTheLegacyBeaconWasDeleted() throws Exception {
    final var legacyBeaconId = createLegacyBeacon();
    final var userId = UUID.randomUUID().toString();
    String reason = "I do not recognise this beacon.";

    final var deleteLegacyBeaconRequest = Files
      .readString(
        Paths.get("src/test/resources/fixtures/deleteLegacyBeaconRequest.json")
      )
      .replace("replace-with-test-legacy-beacon-id", legacyBeaconId)
      .replace("replace-with-test-user-id", userId)
      .replace("replace-with-test-reason", reason);

    webTestClient
      .patch()
      .uri("/legacy-beacon/" + legacyBeaconId + "/delete")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(deleteLegacyBeaconRequest)
      .exchange()
      .expectStatus()
      .isOk();
  }

  private String createLegacyBeacon() throws Exception {
    final var createLegacyBeaconRequest = Files.readString(
      Paths.get("src/test/resources/fixtures/createLegacyBeaconRequest.json")
    );

    return webTestClient
      .post()
      .uri("/migrate/legacy-beacon")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createLegacyBeaconRequest)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody(ObjectNode.class)
      .returnResult()
      .getResponseBody()
      .get("data")
      .get("id")
      .textValue();
  }
}
