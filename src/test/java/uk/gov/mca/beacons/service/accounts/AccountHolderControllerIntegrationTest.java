package uk.gov.mca.beacons.service.accounts;

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
}
