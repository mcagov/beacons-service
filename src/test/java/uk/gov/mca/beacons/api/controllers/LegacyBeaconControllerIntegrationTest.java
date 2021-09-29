package uk.gov.mca.beacons.api.controllers;

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

  @Test
  void shouldShowClaimedLegacyBeaconsAsWithdrawnWithReason() throws Exception {
    // Arrange
    TestSeeder testSeeder = new TestSeeder(webTestClient);
    final var email = UUID.randomUUID() + "@test.com";
    final var legacyBeaconHexId = "1D0EA08C52FFBFF";
    final var createAccountResponseBody = testSeeder.seedAccountHolderWithEmail(
      email
    );
    final var accountHolderId = testSeeder.getAccountHolderId(
      createAccountResponseBody
    );
    WebTestClient.BodyContentSpec seededLegacyBeaconResponseBody = testSeeder.seedLegacyBeaconWithEmailAndHexId(
      email,
      legacyBeaconHexId
    );
    String legacyBeaconId = testSeeder.getLegacyBeaconId(
      seededLegacyBeaconResponseBody
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

    // assertions
    webTestClient
      .post()
      .uri("/registrations/register")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(requestBody)
      .exchange()
      .expectStatus()
      .isCreated();

    // Act
    webTestClient
      .get()
      .uri("/legacy-beacon/" + legacyBeaconId)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .jsonPath("$.data.attributes.beacon.isWithdrawn")
      .isEqualTo(true);
  }
}
