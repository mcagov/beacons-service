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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.services.AccountHolderService;

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
  private AccountHolderService accountHolderService;

  @Test
  void givenNewValidRegistration_whenPosted_thenStatus201() throws IOException {
    final UUID testAccountHolderId = createTestAccountHolder();
    final Object requestBody = toJson(
      readRegistrationsJson()
        .replace(
          "replace-with-test-account-holder-id",
          testAccountHolderId.toString()
        )
    )
      .get(RegistrationUseCase.SINGLE_BEACON);

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
  @Disabled(
    "Disabled until registration dtos/mappers/validation defined for registrations endpoint"
  )
  void givenInvalidRegistration_whenPosted_thenStatus400(
    RegistrationUseCase registrationUseCase
  ) throws IOException {
    final UUID testAccountHolderId = createTestAccountHolder();
    final Object requestBody = toJson(
      readRegistrationsJson()
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

  @Nested
  class UpdateRegistrations {

    private String testAccountHolderId;
    private String beaconId;

    @BeforeEach
    void init() throws Exception {
      testAccountHolderId = createTestAccountHolder().toString();
      final Object requestBody = toJson(
        readRegistrationsJson()
          .replace("replace-with-test-account-holder-id", testAccountHolderId)
      )
        .get(RegistrationUseCase.SINGLE_BEACON);

      beaconId =
        (String) makePostRequest(requestBody)
          .expectBody(Map.class)
          .returnResult()
          .getResponseBody()
          .get("id");
    }

    @Test
    void shouldReturnHttpStatus200OnValidPatch() throws Exception {
      final Object updateRequestBody = toJson(
        readRegistrationsJson()
          .replace("replace-with-test-account-holder-id", testAccountHolderId)
      )
        .get(RegistrationUseCase.BEACON_TO_UPDATE);

      webTestClient
        .patch()
        .uri("/registrations/register/" + beaconId)
        .bodyValue(updateRequestBody)
        .exchange()
        .expectStatus()
        .isOk();
    }

    @Test
    void shouldNotUpdateNonEditableBeaconFields() throws Exception {
      final Object updateRequestBody = toJson(
        readRegistrationsJson()
          .replace("1D0EA08C52FFBFF", "1D0EA08C52FFBFD")
          .replace(
            "replace-with-test-account-holder-id",
            UUID.randomUUID().toString()
          )
          .replace("ABCDE4", "ABCDE5")
      )
        .get(RegistrationUseCase.BEACON_TO_UPDATE);

      webTestClient
        .patch()
        .uri("/registrations/register/" + beaconId)
        .bodyValue(updateRequestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.hexId")
        .isEqualTo("1D0EA08C52FFBFF")
        .jsonPath("$.accountHolderId")
        .isEqualTo(testAccountHolderId)
        .jsonPath("$.referenceNumber")
        .isEqualTo("ABCDE4");
    }

    @Test
    void shouldReplaceTheBeaconUses() throws Exception {
      final Object updateRequestBody = toJson(
        readRegistrationsJson()
          .replace("replace-with-test-account-holder-id", testAccountHolderId)
      )
        .get(RegistrationUseCase.BEACON_TO_UPDATE);

      webTestClient
        .patch()
        .uri("/registrations/register/" + beaconId)
        .bodyValue(updateRequestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.uses.length()")
        .isEqualTo(1);
    }

    @Test
    void shouldReplaceTheOwner() throws Exception {
      final Object updateRequestBody = toJson(
        readRegistrationsJson()
          .replace("replace-with-test-account-holder-id", testAccountHolderId)
      )
        .get(RegistrationUseCase.BEACON_TO_UPDATE);

      webTestClient
        .patch()
        .uri("/registrations/register/" + beaconId)
        .bodyValue(updateRequestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.owner.fullName")
        .isEqualTo("Nice-Admiral Sergio Nelson, 2nd Viscount Nelson")
        .jsonPath("$.owner.email")
        .isEqualTo("sergio@royalnavy.esp")
        .jsonPath("$.owner.telephoneNumber")
        .isEqualTo("02392 85666")
        .jsonPath("$.owner.addressLine1")
        .isEqualTo("2 The Soft")
        .jsonPath("$.owner.townOrCity")
        .isEqualTo("Granada")
        .jsonPath("$.owner.county")
        .isEqualTo("Espanol")
        .jsonPath("$.owner.postcode")
        .isEqualTo("ES10 2DG");
    }
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

    return accountHolderService.create(createAccountHolderRequest).getId();
  }

  private String readRegistrationsJson() throws IOException {
    return Files.readString(Paths.get(REGISTRATION_JSON_RESOURCE));
  }

  enum RegistrationUseCase {
    SINGLE_BEACON,
    BEACON_TO_UPDATE,
    NO_HEX_ID,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }
}
