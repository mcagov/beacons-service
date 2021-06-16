package uk.gov.mca.beacons.service.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AccountHolderControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  void requestCreateAccountHolder_shouldRespondWithTheCreatedResource()
    throws Exception {
    String testAuthId = UUID.randomUUID().toString();
    String newAccountHolderRequest = new String(
      Files.readAllBytes(
        Paths.get("src/test/resources/fixtures/createAccountHolderRequest.json")
      )
    )
      .replace("replace-with-test-auth-id", testAuthId);
    String expectedResponse = new String(
      Files.readAllBytes(
        Paths.get(
          "src/test/resources/fixtures/createAccountHolderResponse.json"
        )
      )
    )
      .replace("replace-with-test-auth-id", testAuthId);

    webTestClient
            .post()
            .uri("/account-holder")
            .body(BodyInserters.fromValue(newAccountHolderRequest))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectBody()
            .json(expectedResponse);
  }

  @Test
  void requestGetAccountHolder_shouldRespondWithTheExistingAccountHolder() throws Exception {
    String testAuthId = UUID.randomUUID().toString();
    String newAccountHolderRequest = new String(
            Files.readAllBytes(
                    Paths.get(
                            "src/test/resources/fixtures/createAccountHolderRequest.json"
                    )
            )
    ).replace("replace-with-test-auth-id", testAuthId);
    var objectMapper = new ObjectMapper();
    var response = webTestClient
            .post()
            .uri("/account-holder")
            .body(BodyInserters.fromValue(newAccountHolderRequest))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .exchange()
            .expectBody()
            .returnResult()
            .getResponseBody();
    final String createdAccountHolderId = objectMapper.readValue(response, ObjectNode.class).get("data").get("id").toString();

    String expectedResponse = new String(
            Files.readAllBytes(
                    Paths.get(
                            "src/test/resources/fixtures/getAccountHolderByIdResponse.json"
                    )
            )
    ).replace("replace-with-test-id", createdAccountHolderId);

    webTestClient
            .get()
            .uri("/account-holder/" + createdAccountHolderId)
            .exchange()
            .expectBody()
            .json(expectedResponse);
  }
}
