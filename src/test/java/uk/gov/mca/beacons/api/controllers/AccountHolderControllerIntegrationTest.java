package uk.gov.mca.beacons.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AccountHolderControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

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
        .uri("/account-holder")
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
        .uri("/account-holder/auth-id/" + authId)
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
        .uri("/account-holder/auth-id/" + nonExistentAuthId)
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
        .uri("/account-holder/" + createdAccountHolderId)
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
        .uri("/account-holder/" + nonExistentAuthId)
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
        .uri("/account-holder/" + accountHolderId + "/beacons")
        .exchange()
        .expectBody()
        .json(expectedResponse);
    }

    @Test
    void shouldRespondWithTheListOfBeaconsForTheAccountHolder()
      throws Exception {
      final String createdAccountHolderId = createAccountHolder();

      final String createBeaconRequest = readFile(
        "src/test/resources/fixtures/createBeaconRequest.json"
      )
        .replace("account-holder-id-placeholder", createdAccountHolderId);
      webTestClient
        .post()
        .uri("/registrations/register")
        .bodyValue(createBeaconRequest)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isCreated();

      final String expectedResponse = readFile(
        "src/test/resources/fixtures/getBeaconsByAccountHolderResponse.json"
      );
      webTestClient
        .get()
        .uri("/account-holder/" + createdAccountHolderId + "/beacons")
        .exchange()
        .expectBody()
        .json(expectedResponse);
    }
  }

  @Nested
  class UpdateAccountHolderIdByAuthId {

    @Test
    void shouldReturnTheIdForTheAccountHolderByAuthId() throws Exception {
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
        .uri("/account-holder/" + accountHolderId)
        .body(BodyInserters.fromValue(updateRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectBody();

      response.json(expectedResponse);
      response.jsonPath("$.data.id").isNotEmpty();
    }
  }

  private String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }

  private String createAccountHolder() throws Exception {
    return createAccountHolder(UUID.randomUUID().toString());
  }

  private String createAccountHolder(String testAuthId) throws Exception {
    final String newAccountHolderRequest = readFile(
      "src/test/resources/fixtures/createAccountHolderRequest.json"
    )
      .replace("replace-with-test-auth-id", testAuthId);
    final var createdAccountResponse = webTestClient
      .post()
      .uri("/account-holder")
      .body(BodyInserters.fromValue(newAccountHolderRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
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
