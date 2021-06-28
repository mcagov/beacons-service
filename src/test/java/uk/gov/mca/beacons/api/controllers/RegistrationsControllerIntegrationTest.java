package uk.gov.mca.beacons.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.services.CreateAccountHolderService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class RegistrationsControllerIntegrationTest {

  private static final String REGISTRATION_ENDPOINT = "/registrations/register";
  private static final String REGISTRATION_JSON_RESOURCE =
    "src/test/resources/fixtures/registrations.json";
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private CreateAccountHolderService createAccountHolderService;

  @ParameterizedTest
  @EnumSource(
    value = RegistrationUseCase.class,
    names = { "SINGLE_BEACON", "MULTIPLE_BEACONS" }
  )
  void givenNewValidRegistration_whenPosted_thenStatus201(
    RegistrationUseCase registrationUseCase
  ) throws IOException {
    UUID testAccountHolderId = createTestAccountHolder();
    var requestBody = toJson(
      readFile(REGISTRATION_JSON_RESOURCE)
        .replace(
          "replace-with-test-account-holder-id",
          testAccountHolderId.toString()
        )
    )
      .get(registrationUseCase);

    makePostRequest(requestBody)
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
    RegistrationUseCase registrationUseCase
  ) throws IOException {
    UUID testAccountHolderId = createTestAccountHolder();
    var requestBody = toJson(
      readFile(REGISTRATION_JSON_RESOURCE)
        .replace(
          "replace-with-test-account-holder-id",
          testAccountHolderId.toString()
        )
    )
      .get(registrationUseCase);

    makePostRequest(requestBody)
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

  private Map<RegistrationUseCase, Object> toJson(String registrationFixture)
    throws JsonProcessingException {
    final TypeReference<EnumMap<RegistrationUseCase, Object>> typeReference = new TypeReference<>() {};
    return OBJECT_MAPPER.readValue(registrationFixture, typeReference);
  }

  private UUID createTestAccountHolder() {
    CreateAccountHolderRequest createAccountHolderRequest = CreateAccountHolderRequest
      .builder()
      .authId(UUID.randomUUID().toString())
      .email("testy@mctestface.com")
      .fullName("Tesy McTestface")
      .telephoneNumber("01178 657123")
      .alternativeTelephoneNumber("01178 657124")
      .addressLine1("Flat 42")
      .addressLine2("Testington Towers")
      .addressLine3("Tower Test")
      .addressLine4("Testing Towers")
      .townOrCity("Testville")
      .postcode("TS1 23A")
      .county("Testershire")
      .build();

    return createAccountHolderService
      .execute(createAccountHolderRequest)
      .getId();
  }

  private String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }

  enum RegistrationUseCase {
    SINGLE_BEACON,
    MULTIPLE_BEACONS,
    NO_HEX_ID,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }
}
