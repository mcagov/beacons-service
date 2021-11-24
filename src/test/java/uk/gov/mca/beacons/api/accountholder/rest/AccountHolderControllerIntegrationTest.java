package uk.gov.mca.beacons.api.accountholder.rest;

import com.jayway.jsonpath.JsonPath;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import javax.transaction.Transactional;
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
public class AccountHolderControllerIntegrationTest {

  private final String accountHolderEndpoint = "/spring-api/account-holderv2";

  @Autowired
  private WebTestClient webTestClient;

  @Transactional
  @Test
  public void shouldRespondWithTheCreatedAccountHolder() throws Exception {
    final UUID authId = UUID.randomUUID();
    final var createAccountHolderResponse = createAccountHolderResponse(authId);

    createAccountHolder(authId)
      .expectStatus()
      .isCreated()
      .expectBody()
      .json(createAccountHolderResponse)
      .jsonPath("$.data.id")
      .isNotEmpty();
  }

  @Transactional
  @Test
  public void shouldFindTheCreatedAccountHolderById() throws Exception {
    final UUID authId = UUID.randomUUID();
    final var createAccountHolderResponse = createAccountHolderResponse(authId);

    String responseBody = createAccountHolder(authId)
      .returnResult(String.class)
      .getResponseBody()
      .blockFirst();

    String id = JsonPath.read(responseBody, "$.data.id");

    webTestClient
      .get()
      .uri(accountHolderEndpoint + "/" + id)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(createAccountHolderResponse);
  }

  private WebTestClient.ResponseSpec createAccountHolder(UUID authId)
    throws Exception {
    final var createAccountHolderRequest = createAccountHolderRequest(authId);

    return webTestClient
      .post()
      .uri(accountHolderEndpoint)
      .body(BodyInserters.fromValue(createAccountHolderRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange();
  }

  private String createAccountHolderRequest(UUID authId) throws Exception {
    return Files
      .readString(
        Paths.get("src/test/resources/fixtures/createAccountHolderRequest.json")
      )
      .replace("replace-with-test-auth-id", authId.toString());
  }

  private String createAccountHolderResponse(UUID authId) throws Exception {
    return Files
      .readString(
        Paths.get(
          "src/test/resources/fixtures/createAccountHolderResponse.json"
        )
      )
      .replace("replace-with-test-auth-id", authId.toString());
  }
}
