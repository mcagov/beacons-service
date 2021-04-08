package uk.gov.mca.beacons.service.registrations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RegistrationsControllerIntegrationTest {

  private static final String REGISTRATION_ENDPOINT = "/registrations/register";
  private static final String REGISTRATION_JSON_RESOURCE =
    "src/test/resources/registrations.json";
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired
  private WebTestClient webTestClient;

  @ParameterizedTest
  @EnumSource(
    value = RegistrationJson.class,
    names = { "SINGLE_BEACON", "MULTIPLE_BEACON" }
  )
  void givenNewValidRegistration_whenPosted_thenStatus201(
    RegistrationJson registrationJson
  ) throws IOException {
    final Map<RegistrationJson, Object> validRegistrationRequestBody = getRegistrationJson();

    makePostRequest(validRegistrationRequestBody.get(registrationJson))
      .expectStatus()
      .isCreated()
      .expectHeader()
      .valueEquals("Content-Type", "application/json");
  }

  @ParameterizedTest
  @EnumSource(
    value = RegistrationJson.class,
    names = { "NO_BEACON_TYPE", "NO_USES", "NO_EMERGENCY_CONTACTS" }
  )
  void givenNewInvalidRegistration_whenPosted_thenStatus400(
    RegistrationJson registrationJson
  ) throws IOException {
    final Map<RegistrationJson, Object> validRegistrationRequestBody = getRegistrationJson();

    makePostRequest(validRegistrationRequestBody.get(registrationJson))
      .expectStatus()
      .is4xxClientError()
      .expectHeader()
      .valueEquals("Content-Type", "application/json");
  }

  private WebTestClient.ResponseSpec makePostRequest(Object json) {
    return webTestClient
      .post()
      .uri(REGISTRATION_ENDPOINT)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(json)
      .exchange();
  }

  private Map<RegistrationJson, Object> getRegistrationJson()
    throws IOException {
    final String json = new String(
      Files.readAllBytes(Paths.get(REGISTRATION_JSON_RESOURCE))
    );
    final TypeReference<EnumMap<RegistrationJson, Object>> typeReference = new TypeReference<>() {};
    return OBJECT_MAPPER.readValue(json, typeReference);
  }

  enum RegistrationJson {
    SINGLE_BEACON,
    MULTIPLE_BEACON,
    NO_BEACON_TYPE,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }
}
