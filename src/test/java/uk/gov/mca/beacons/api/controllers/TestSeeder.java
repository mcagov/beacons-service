package uk.gov.mca.beacons.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

public class TestSeeder {

  private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private final WebTestClient webTestClient;

  public TestSeeder(WebTestClient webTestClient) {
    this.webTestClient = webTestClient;
  }

  public String readFile(String filePath) throws IOException {
    return Files.readString(Paths.get(filePath));
  }

  public WebTestClient.BodyContentSpec seedBeacon(
    Function<String, String> replacer
  ) throws Exception {
    final var createBeaconRequest = replacer.apply(
      readFile("src/test/resources/fixtures/createBeaconRequest.json")
    );

    return webTestClient
      .post()
      .uri("/spring-api/registrations/register")
      .body(BodyInserters.fromValue(createBeaconRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody();
  }

  public WebTestClient.BodyContentSpec seedLegacyBeaconWithEmailAndHexId(
    String email,
    String hexId
  ) throws Exception {
    final var createLegacyBeaconRequest = Files
      .readString(
        Paths.get("src/test/resources/fixtures/createLegacyBeaconRequest.json")
      )
      .replace("ownerbeacon@beacons.com", email)
      .replace("9D0E1D1B8C00001", hexId);

    return webTestClient
      .post()
      .uri("/spring-api/migrate/legacy-beacon")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(createLegacyBeaconRequest)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody();
  }

  public WebTestClient.BodyContentSpec seedAccountHolderWithEmail(String email)
    throws Exception {
    String newAccountHolderRequest = readFile(
      "src/test/resources/fixtures/createAccountHolderRequest.json"
    )
      .replace("testy@mctestface.com", email)
      .replace("replace-with-test-auth-id", UUID.randomUUID().toString());

    return webTestClient
      .post()
      .uri("/spring-api/account-holder")
      .body(BodyInserters.fromValue(newAccountHolderRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectBody();
  }

  public WebTestClient.BodyContentSpec seedAccountHolderWithAuthId(
    String testAuthId
  ) throws Exception {
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
      .expectBody();
  }

  public String getAccountHolderId(
    WebTestClient.BodyContentSpec createAccountResponse
  ) throws Exception {
    return OBJECT_MAPPER
      .readValue(
        createAccountResponse.returnResult().getResponseBody(),
        ObjectNode.class
      )
      .get("data")
      .get("id")
      .textValue();
  }

  public String getLegacyBeaconId(
    WebTestClient.BodyContentSpec createLegacyBeaconResponse
  ) throws Exception {
    return OBJECT_MAPPER
      .readValue(
        createLegacyBeaconResponse.returnResult().getResponseBody(),
        ObjectNode.class
      )
      .get("data")
      .get("id")
      .textValue();
  }

  public String getLegacyBeaconHexId(
    WebTestClient.BodyContentSpec createLegacyBeaconResponse
  ) throws Exception {
    return OBJECT_MAPPER
      .readValue(
        createLegacyBeaconResponse.returnResult().getResponseBody(),
        ObjectNode.class
      )
      .get("data")
      .get("attributes")
      .get("beacon")
      .get("hexId")
      .textValue();
  }

  public String getLegacyBeaconEmail(
    WebTestClient.BodyContentSpec createLegacyBeaconResponse
  ) throws Exception {
    return OBJECT_MAPPER
      .readValue(
        createLegacyBeaconResponse.returnResult().getResponseBody(),
        ObjectNode.class
      )
      .get("data")
      .get("attributes")
      .get("owner")
      .get("email")
      .textValue();
  }

  public Map<RegistrationsControllerIntegrationTest.RegistrationUseCase, Object> registrationFixtureToMap(
    String registrationFixture
  ) throws JsonProcessingException {
    final TypeReference<EnumMap<RegistrationsControllerIntegrationTest.RegistrationUseCase, Object>> typeReference = new TypeReference<>() {};
    return OBJECT_MAPPER.readValue(registrationFixture, typeReference);
  }
}
