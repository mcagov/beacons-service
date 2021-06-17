package uk.gov.mca.beacons.service.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.Environment;
import uk.gov.mca.beacons.service.registrations.RegistrationsService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AccountHolderControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void requestCreateAccountHolder_shouldRespondWithTheCreatedResource()
    throws Exception {
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

  @Test
  void requestGetAccountHolder_shouldRespondWithTheExistingAccountHolder()
    throws Exception {
    String testAuthId = UUID.randomUUID().toString();
    String newAccountHolderRequest = readFile(
      "src/test/resources/fixtures/createAccountHolderRequest.json"
    )
      .replace("replace-with-test-auth-id", testAuthId);
    var createdAccountResponse = webTestClient
      .post()
      .uri("/account-holder")
      .body(BodyInserters.fromValue(newAccountHolderRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectBody()
      .returnResult()
      .getResponseBody();
    String createdAccountHolderId = new ObjectMapper()
      .readValue(createdAccountResponse, ObjectNode.class)
      .get("data")
      .get("id")
      .textValue();
    String expectedResponse = readFile(
      "src/test/resources/fixtures/getAccountHolderByIdResponse.json"
    )
      .replace("replace-with-test-auth-id", testAuthId);

    var response = webTestClient
      .get()
      .uri("/account-holder/" + createdAccountHolderId)
      .exchange()
      .expectBody();

    response.json(expectedResponse);
    response.jsonPath("$.data.id").exists();
  }

  @Test
  void requestGetBeaconsByAccountHolderId_shouldRespondWithAnEmptyListOfBeacons()
    throws Exception {
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
  void requestGetBeaconsByAccountHolderId_shouldRespondWithTheListOfBeaconsForTheAccountHolder()
    throws Exception {
    final String expectedResponse = readFile(
      "src/test/resources/fixtures/getBeaconsByAccountHolderResponse.json"
    );

    final String accountHolderId = UUID.randomUUID().toString();

    webTestClient
      .get()
      .uri("/account-holder/" + accountHolderId + "/beacons")
      .exchange()
      .expectBody()
      .json(expectedResponse);
  }

  private String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }
}
