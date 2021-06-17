package uk.gov.mca.beacons.service.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
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
    response.jsonPath("$.data.id").exists();
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
      .replace("replace-with-test-id", createdAccountHolderId)
      .replace("replace-with-test-auth-id", testAuthId);

    webTestClient
      .get()
      .uri("/account-holder/" + createdAccountHolderId)
      .exchange()
      .expectBody()
      .json(expectedResponse);
  }

  private String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }
}
