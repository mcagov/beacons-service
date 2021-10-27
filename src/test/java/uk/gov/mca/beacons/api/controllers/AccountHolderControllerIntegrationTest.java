package uk.gov.mca.beacons.api.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
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
import uk.gov.mca.beacons.api.dto.DeleteBeaconRequestDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AccountHolderControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  private String readFile(String filePath) throws IOException {
    return Files.readString(Paths.get(filePath));
  }

  private String createAccountHolder() throws Exception {
    return createAccountHolder(UUID.randomUUID().toString());
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

  private String createBeacon(String accountHolderId) throws Exception {
    final String createBeaconRequest = readFile(
      "src/test/resources/fixtures/createBeaconRequest.json"
    )
      .replace("account-holder-id-placeholder", accountHolderId);

    return webTestClient
      .post()
      .uri("/spring-api/registrations/register")
      .bodyValue(createBeaconRequest)
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody(ObjectNode.class)
      .returnResult()
      .getResponseBody()
      .get("id")
      .textValue();
  }

  @Nested
  class CreateAccountHolder {

    @Test
    void shouldRespondWithTheCreatedResource() throws Exception {
      String testAuthId = UUID.randomUUID().toString();
      String newAccountHolderRequest = readFile(
        "src/test/resources/fixtures/createAccountHolderRequest.json"
      )
        .replace("replace-with-test-auth-id", testAuthId);
      String expectedResponse = readFile(
        "src/test/resources/fixtures/createAccountHolderResponse.json"
      )
        .replace("replace-with-test-auth-id", testAuthId);

      var response = webTestClient
        .post()
        .uri("/spring-api/account-holder")
        .body(BodyInserters.fromValue(newAccountHolderRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectBody();

      response.json(expectedResponse);
      response.jsonPath("$.data.id").isNotEmpty();
    }
  }

  @Nested
  class GetAccountHolderIdByAuthId {

    @Test
    void shouldReturnTheIdForTheAccountHolderByAuthId() throws Exception {
      final var authId = UUID.randomUUID().toString();
      final var accountHolderId = createAccountHolder(authId);

      webTestClient
        .get()
        .uri("/spring-api/account-holder/auth-id/" + authId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id", accountHolderId);
    }

    @Test
    void shouldReturn404IfTheAccountHolderDoesNotExist() {
      final var nonExistentAuthId = UUID.randomUUID().toString();

      webTestClient
        .get()
        .uri("/spring-api/account-holder/auth-id/" + nonExistentAuthId)
        .exchange()
        .expectStatus()
        .isNotFound();
    }
  }

  @Nested
  class GetAccountHolderById {

    @Test
    void shouldRespondWithTheExistingAccountHolder() throws Exception {
      final String testAuthId = UUID.randomUUID().toString();
      final String createdAccountHolderId = createAccountHolder(testAuthId);
      final String expectedResponse = readFile(
        "src/test/resources/fixtures/getAccountHolderByIdResponse.json"
      )
        .replace("replace-with-test-auth-id", testAuthId);

      var response = webTestClient
        .get()
        .uri("/spring-api/account-holder/" + createdAccountHolderId)
        .exchange()
        .expectBody();

      response.json(expectedResponse);
      response.jsonPath("$.data.id").isNotEmpty();
    }

    @Test
    void shouldRespondWithA404IfTheAccountHolderIsNotFound() {
      final var nonExistentAuthId = UUID.randomUUID().toString();

      webTestClient
        .get()
        .uri("/spring-api/account-holder/" + nonExistentAuthId)
        .exchange()
        .expectStatus()
        .isNotFound();
    }
  }

  @Nested
  class GetBeaconsByAccountHolderId {

    @Test
    void shouldRespondWithAnEmptyListOfBeacons() throws Exception {
      final String expectedResponse = readFile(
        "src/test/resources/fixtures/getBeaconsByAccountHolderEmptyResponse.json"
      );

      final String accountHolderId = UUID.randomUUID().toString();

      webTestClient
        .get()
        .uri("/spring-api/account-holder/" + accountHolderId + "/beacons")
        .exchange()
        .expectBody()
        .json(expectedResponse);
    }

    @Test
    void shouldRespondWithTheListOfBeaconsForTheAccountHolder()
      throws Exception {
      final String createdAccountHolderId = createAccountHolder();
      createBeacon(createdAccountHolderId);

      final String expectedResponse = readFile(
        "src/test/resources/fixtures/getBeaconsByAccountHolderResponse.json"
      );
      webTestClient
        .get()
        .uri(
          "/spring-api/account-holder/" + createdAccountHolderId + "/beacons"
        )
        .exchange()
        .expectBody()
        .json(expectedResponse);
    }

    @Test
    void shouldRespondWithAnEmptyListIfAllBeaconsHaveBeenDeletedFromTheAccount()
      throws Exception {
      final String createdAccountHolderId = createAccountHolder();
      final String beaconId = createBeacon(createdAccountHolderId);
      final var deleteBeaconRequest = DeleteBeaconRequestDTO
        .builder()
        .beaconId(UUID.fromString(beaconId))
        .userId(UUID.fromString(createdAccountHolderId))
        .reason("Not used on my boat")
        .build();

      webTestClient
        .patch()
        .uri("/spring-api/beacons/" + beaconId + "/delete")
        .body(BodyInserters.fromValue(deleteBeaconRequest))
        .exchange()
        .expectStatus()
        .isOk();

      final String expectedResponse = readFile(
        "src/test/resources/fixtures/getBeaconsByAccountHolderEmptyResponse.json"
      );
      webTestClient
        .get()
        .uri(
          "/spring-api/account-holder/" + createdAccountHolderId + "/beacons"
        )
        .exchange()
        .expectBody()
        .json(expectedResponse);
    }
  }

  @Nested
  class UpdateAccountHolderIdByAuthId {

    @Test
    void shouldReturnTheUpdatedAccountHolderByAuthId() throws Exception {
      final var authId = UUID.randomUUID().toString();
      final var accountHolderId = createAccountHolder(authId);

      String updateRequest = readFile(
        "src/test/resources/fixtures/updateAccountHolderRequest.json"
      )
        .replace("replace-with-test-account-id", accountHolderId);
      String expectedResponse = readFile(
        "src/test/resources/fixtures/updateAccountHolderResponse.json"
      )
        .replace("replace-with-test-account-id", accountHolderId);

      var response = webTestClient
        .patch()
        .uri("/spring-api/account-holder/" + accountHolderId)
        .body(BodyInserters.fromValue(updateRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectBody();

      response.json(expectedResponse);
      response.jsonPath("$.data.id").isNotEmpty();
    }
  }
}
