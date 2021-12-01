package uk.gov.mca.beacons.api.accountholder.rest;

import com.jayway.jsonpath.JsonPath;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import uk.gov.mca.beacons.api.DatabaseCleaner;
import uk.gov.mca.beacons.api.DatabaseCleanerConfiguration;
import uk.gov.mca.beacons.api.FixtureHelper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ DatabaseCleanerConfiguration.class })
@AutoConfigureWebTestClient
public class AccountHolderControllerIntegrationTest {

  private final String accountHolderEndpoint = "/spring-api/account-holderv2";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private DatabaseCleaner databaseCleaner;

  @Autowired
  private FixtureHelper fixtureHelper;

  @BeforeEach
  public void init() {
    databaseCleaner.clean();
  }

  @Test
  public void shouldRespondWithTheCreatedAccountHolder() throws Exception {
    final String authId = UUID.randomUUID().toString();
    final var createAccountHolderResponse = createAccountHolderResponse(authId);

    createAccountHolder(authId)
      .expectStatus()
      .isCreated()
      .expectBody()
      .json(createAccountHolderResponse)
      .jsonPath("$.data.id")
      .isNotEmpty();
  }

  @Test
  public void shouldFindTheCreatedAccountHolderById() throws Exception {
    final String authId = UUID.randomUUID().toString();
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

  @Test
  public void shouldFindTheAccountHolderByAuthId() throws Exception {
    final String authId = UUID.randomUUID().toString();
    final var createAccountHolderResponse = createAccountHolderResponse(authId);

    createAccountHolder(authId).expectStatus().isCreated();

    webTestClient
      .get()
      .uri(accountHolderEndpoint + "?authId=" + authId)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(createAccountHolderResponse);
  }

  @Test
  public void shouldRespondWithTheUpdateAccountHolderDetails()
    throws Exception {
    final String authId = UUID.randomUUID().toString();

    String responseBody = createAccountHolder(authId)
      .returnResult(String.class)
      .getResponseBody()
      .blockFirst();

    String id = JsonPath.read(responseBody, "$.data.id");

    String updateAccountHolderRequest = updateAccountHolderRequest(id);
    String updateAccountHolderResponse = updateAccountHolderResponse(id);

    webTestClient
      .patch()
      .uri(accountHolderEndpoint + "/" + id)
      .body(BodyInserters.fromValue(updateAccountHolderRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(updateAccountHolderResponse);
  }

  private WebTestClient.ResponseSpec createAccountHolder(String authId)
    throws Exception {
    final var createAccountHolderRequest = createAccountHolderRequest(authId);

    return webTestClient
      .post()
      .uri(accountHolderEndpoint)
      .body(BodyInserters.fromValue(createAccountHolderRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange();
  }

  private String createAccountHolderRequest(String authId) throws Exception {
    return fixtureHelper.getFixture(
      "src/test/resources/fixtures/createAccountHolderRequest.json",
      fixture -> fixture.replace("replace-with-test-auth-id", authId)
    );
  }

  private String createAccountHolderResponse(String authId) throws Exception {
    return fixtureHelper.getFixture(
      "src/test/resources/fixtures/createAccountHolderResponse.json",
      fixture -> fixture.replace("replace-with-test-auth-id", authId)
    );
  }

  private String updateAccountHolderRequest(String id) throws Exception {
    return fixtureHelper.getFixture(
      "src/test/resources/fixtures/updateAccountHolderResponse.json",
      fixture -> fixture.replace("replace-with-test-account-id", id)
    );
  }

  private String updateAccountHolderResponse(String id) throws Exception {
    return fixtureHelper.getFixture(
      "src/test/resources/fixtures/updateAccountHolderResponse.json",
      fixture -> fixture.replace("replace-with-test-account-id", id)
    );
  }
}
