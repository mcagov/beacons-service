package uk.gov.mca.beacons.api;

import com.jayway.jsonpath.JsonPath;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.mca.beacons.api.registration.rest.RegistrationControllerIntegrationTest;

@AutoConfigureWebTestClient
public abstract class WebIntegrationTest extends BaseIntegrationTest {

  @Autowired
  WebTestClient webTestClient;

  protected enum Endpoints {
    AccountHolder("/spring-api/account-holderv2"),
    Beacon("/spring-api/beaconsv2"),
    LegacyBeacon("/spring-api/legacy-beaconv2"),
    Migration("/spring-api/migratev2"),
    Note("/spring-api/notev2"),
    Registration("/spring-api/registrationv2");

    public final String label;

    Endpoints(String label) {
      this.label = label;
    }
  }

  // TEST SEEDERS

  /**
   *
   * @param authId random UUID as String
   * @return AccountHolderId as String
   * @throws Exception from reading fixture
   */
  protected String seedAccountHolder(String authId) throws Exception {
    final String createAccountHolderRequest = fixtureHelper.getFixture(
      "src/test/resources/fixtures/createAccountHolderRequest.json",
      fixture -> fixture.replace("replace-with-test-auth-id", authId)
    );

    return JsonPath.read(
      webTestClient
        .post()
        .uri(Endpoints.AccountHolder.toString())
        .body(BodyInserters.fromValue(createAccountHolderRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .returnResult(String.class)
        .getResponseBody()
        .blockFirst(),
      "$.data.id"
    );
  }

  /**
   *
   * @return AccountHolderId as String
   * @throws Exception From reading fixture
   */
  private String seedAccountHolder() throws Exception {
    return seedAccountHolder(UUID.randomUUID().toString());
  }

  /**
   *
   * @return LegacyBeaconId as String
   * @throws Exception from reading fixture
   */
  private String seedLegacyBeacon() throws Exception {
    String createLegacyBeaconRequest = fixtureHelper.getFixture(
      "src/test/resources/fixtures/createLegacyBeaconRequest.json"
    );

    return JsonPath.read(
      webTestClient
        .post()
        .uri(Endpoints.LegacyBeacon + "/legacy-beacon")
        .body(BodyInserters.fromValue(createLegacyBeaconRequest))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .returnResult(String.class)
        .getResponseBody()
        .blockFirst(),
      "$.data.id"
    );
  }

  protected enum RegistrationUseCase {
    SINGLE_BEACON,
    BEACON_TO_UPDATE,
    NO_HEX_ID,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }

  /**
   *
   * @param useCase RegistrationUseCase
   * @param accountHolderId AccountHolderId to associate with registration
   * @return id of the registered Beacon
   * @throws Exception from reading fixture
   */
  protected String seedRegistration(
    RegistrationUseCase useCase,
    String accountHolderId
  ) throws Exception {
    final String registrationBody = getRegistrationBody(
      useCase,
      accountHolderId
    );
    return JsonPath.read(
      webTestClient
        .post()
        .uri(Endpoints.Registration + "/register")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(registrationBody)
        .exchange()
        .returnResult(String.class)
        .getResponseBody()
        .blockFirst(),
      "$.id"
    );
  }

  private String getRegistrationBody(
    RegistrationUseCase useCase,
    String accountHolderId
  ) throws Exception {
    final String REGISTRATION_JSON_RESOURCE =
      "src/test/resources/fixtures/registrations.json";

    final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    final Map<String, Map<String, Object>> registrationMap = mapper.readValue(
      fixtureHelper.getFixture(
        REGISTRATION_JSON_RESOURCE,
        fixture ->
          fixture.replace(
            "replace-with-test-account-holder-id",
            accountHolderId
          )
      ),
      HashMap.class
    );

    return mapper.writeValueAsString(registrationMap.get(useCase.toString()));
  }
}
