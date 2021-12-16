package uk.gov.mca.beacons.api.registration.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.JsonPath;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.mca.beacons.api.BaseIntegrationTest;

@AutoConfigureWebTestClient
public class RegistrationControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  WebTestClient webTestClient;

  private static final String REGISTRATION_ENDPOINT =
    "/spring-api/registrationsv2";

  enum RegistrationUseCase {
    SINGLE_BEACON,
    BEACON_TO_UPDATE,
    NO_HEX_ID,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }

  private String accountHolderId;

  @BeforeEach
  void init() throws Exception {
    accountHolderId = createAccountHolder();
  }

  @Nested
  class NewRegistration {

    @Test
    void shouldRegisterNewRegistration() throws Exception {
      final String registrationBody = getRegistrationBody(
        RegistrationUseCase.SINGLE_BEACON
      );

      webTestClient
        .post()
        .uri(REGISTRATION_ENDPOINT + "/register")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(registrationBody)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .expectBody()
        .json(registrationBody);
    }
  }

  // returns created account holder id
  private String createAccountHolder() throws Exception {
    final String accountHolderEndpoint = "/spring-api/account-holderv2";
    String authId = UUID.randomUUID().toString();

    String createAccountHolderRequestBody = fixtureHelper.getFixture(
      "src/test/resources/fixtures/createAccountHolderRequest.json",
      fixture -> fixture.replace("replace-with-test-auth-id", authId)
    );

    return JsonPath.read(
      webTestClient
        .post()
        .uri(accountHolderEndpoint)
        .body(BodyInserters.fromValue(createAccountHolderRequestBody))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .returnResult(String.class)
        .getResponseBody()
        .blockFirst(),
      "$.data.id"
    );
  }

  private String getRegistrationBody(RegistrationUseCase useCase)
    throws Exception {
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
