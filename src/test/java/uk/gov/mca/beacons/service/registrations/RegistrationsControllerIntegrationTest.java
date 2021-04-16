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
    "src/test/resources/fixtures/registrations.json";
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired
  private WebTestClient webTestClient;

  @ParameterizedTest
  @EnumSource(
    value = RegistrationUseCase.class,
    names = { "SINGLE_BEACON", "MULTIPLE_BEACONS" }
  )
  void givenNewValidRegistration_whenPosted_thenStatus201(
    RegistrationUseCase registrationJson
  ) throws IOException {
    final Map<RegistrationUseCase, Object> registrationsJson = getRegistrationsJson();

    makePostRequest(registrationsJson.get(registrationJson))
      .expectStatus()
      .isCreated()
      .expectHeader()
      .valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE);
  }

  @ParameterizedTest
  @EnumSource(
    value = RegistrationUseCase.class,
    names = { "NO_HEX_ID", "NO_USES", "NO_EMERGENCY_CONTACTS" }
  )
  void givenInvalidRegistration_whenPosted_thenStatus400(
    RegistrationUseCase registrationJson
  ) throws IOException {
    final Map<RegistrationUseCase, Object> registrationsJson = getRegistrationsJson();

    makePostRequest(registrationsJson.get(registrationJson))
      .expectStatus()
      .isBadRequest()
      .expectHeader()
      .valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE);
  }

  private WebTestClient.ResponseSpec makePostRequest(Object json) {
    return webTestClient
      .post()
      .uri(REGISTRATION_ENDPOINT)
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(json)
      .exchange();
  }

  private Map<RegistrationUseCase, Object> getRegistrationsJson()
    throws IOException {
    final String json = new String(
      Files.readAllBytes(Paths.get(REGISTRATION_JSON_RESOURCE))
    );
    final TypeReference<EnumMap<RegistrationUseCase, Object>> typeReference = new TypeReference<>() {};
    return OBJECT_MAPPER.readValue(json, typeReference);
  }

  enum RegistrationUseCase {
    SINGLE_BEACON,
    MULTIPLE_BEACONS,
    NO_HEX_ID,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }
}
