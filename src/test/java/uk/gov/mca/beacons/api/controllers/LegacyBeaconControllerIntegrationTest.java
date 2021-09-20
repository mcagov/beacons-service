package uk.gov.mca.beacons.api.controllers;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.gateways.UserGateway;
import uk.gov.mca.beacons.api.services.GetUserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LegacyBeaconControllerIntegrationTest {

  private User user;

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private UserGateway userGateway;

  @BeforeEach
  void init() {
    final var userId = UUID.randomUUID();

    user =
      AccountHolder
        .builder()
        .id(userId)
        .email("beacon@beacons.com")
        .fullName("Mr. Beacon")
        .build();
  }

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
  void shouldUpdateTheStatusOfALegacyBeaconToDeletedWhenItIsDeleted()
    throws Exception {
    final String reason = "I do not recognise this beacon.";
    final var legacyBeaconId = createLegacyBeacon();
    deleteLegacyBeacon(legacyBeaconId, user.getId().toString(), reason);

    final var deleteLegacyBeaconResponse = Files
      .readString(
        Paths.get("src/test/resources/fixtures/createLegacyBeaconResponse.json")
      )
      .replace("MIGRATED", BeaconStatus.DELETED.toString());

    webTestClient
      .get()
      .uri("legacy-beacon/" + legacyBeaconId)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(deleteLegacyBeaconResponse);
  }

  @Test
  void shouldCreateANoteWithTheReasonForDeletionWhenTheLegacyBeaconIsDeleted()
    throws Exception {
    final String reason = "I do not recognise this beacon.";
    final var legacyBeaconId = createLegacyBeacon();
    deleteLegacyBeacon(legacyBeaconId, user.getId().toString(), reason);

    final var noteForDeletedLegacyBeaconResponse = Files
      .readString(
        Paths.get(
          "src/test/resources/fixtures/getNotesByDeletedLegacyBeaconIdResponse.json"
        )
      )
      .replace("replace-with-deleted-legacy-beacon-id", legacyBeaconId)
      .replace("replace-with-user-id", user.getId().toString());

    webTestClient
      .get()
      .uri("legacy-beacon/" + legacyBeaconId + "/notes")
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(noteForDeletedLegacyBeaconResponse);
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

  private void deleteLegacyBeacon(
    String legacyBeaconId,
    String userId,
    String reason
  ) throws Exception {
    final var deleteLegacyBeaconRequest = Files
      .readString(
        Paths.get("src/test/resources/fixtures/deleteLegacyBeaconRequest.json")
      )
      .replace("replace-with-test-legacy-beacon-id", legacyBeaconId)
      .replace("replace-with-test-user-id", userId)
      .replace("replace-with-test-reason", reason);

    given(userGateway.getUserById(user.getId())).willReturn(user);

    webTestClient
      .patch()
      .uri("/legacy-beacon/" + legacyBeaconId + "/delete")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(deleteLegacyBeaconRequest)
      .exchange()
      .expectStatus()
      .isOk();
  }
}
