package uk.gov.mca.beacons.api.controllers;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
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

  private static final String REGISTRATION_JSON_RESOURCE =
    "src/test/resources/fixtures/registrations.json";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private AccountHolderService accountHolderService;

  @Test
  void givenNewValidRegistration_whenPosted_thenStatus201_2()
    throws IOException {
    TestSeeder testSeeder = new TestSeeder(webTestClient);
    final UUID testAccountHolderId = createTestAccountHolder();
    final Object requestBody = testSeeder
      .registrationFixtureToMap(
        testSeeder
          .readFile(REGISTRATION_JSON_RESOURCE)
          .replace(
            "replace-with-test-account-holder-id",
            testAccountHolderId.toString()
          )
      )
      .get(RegistrationUseCase.SINGLE_BEACON);

    webTestClient
      .post()
      .uri("/registrations/register")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(requestBody)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectHeader()
      .valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE);
  }

  @Test
  void givenNewValidRegistration_whenPosted_thenStatus201() throws IOException {
    TestSeeder testSeeder = new TestSeeder(webTestClient);
    final UUID testAccountHolderId = createTestAccountHolder();
    final Object requestBody = testSeeder
      .registrationFixtureToMap(
        testSeeder
          .readFile(REGISTRATION_JSON_RESOURCE)
          .replace(
            "replace-with-test-account-holder-id",
            testAccountHolderId.toString()
          )
      )
      .get(RegistrationUseCase.SINGLE_BEACON);

    webTestClient
      .post()
      .uri("/registrations/register")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(requestBody)
      .exchange()
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
    TestSeeder testSeeder = new TestSeeder(webTestClient);
    final UUID testAccountHolderId = createTestAccountHolder();
    final Object requestBody = testSeeder
      .registrationFixtureToMap(
        testSeeder
          .readFile(REGISTRATION_JSON_RESOURCE)
          .replace(
            "replace-with-test-account-holder-id",
            testAccountHolderId.toString()
          )
      )
      .get(registrationUseCase);

    webTestClient
      .post()
      .uri("/registrations/register")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(requestBody)
      .exchange()
      .expectStatus()
      .isBadRequest()
      .expectHeader()
      .valueEquals("Content-Type", MediaType.APPLICATION_JSON_VALUE);
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

  enum RegistrationUseCase {
    SINGLE_BEACON,
    BEACON_TO_UPDATE,
    NO_HEX_ID,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }

  @Nested
  class UpdateRegistrations {

    private String testAccountHolderId;
    private String beaconId;
    private String lastModifiedDate;

    @BeforeEach
    void init() throws Exception {
      testAccountHolderId = createTestAccountHolder().toString();
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final Object requestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
            .replace("replace-with-test-account-holder-id", testAccountHolderId)
        )
        .get(RegistrationUseCase.SINGLE_BEACON);

      final var responseBody = webTestClient
        .post()
        .uri("/registrations/register")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectBody(ObjectNode.class)
        .returnResult()
        .getResponseBody();

      beaconId = responseBody.get("id").textValue();
      lastModifiedDate = responseBody.get("lastModifiedDate").textValue();
    }

    @Test
    void shouldUpdateTheBeacon() throws Exception {
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final Object updateRequestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
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
        .jsonPath("$.manufacturer")
        .isEqualTo("Ocean Sound")
        .jsonPath("$.model")
        .isEqualTo("EPIRB2")
        .jsonPath("$.manufacturerSerialNumber")
        .isEqualTo("1407312905")
        .jsonPath("$.chkCode")
        .isEqualTo("9480C")
        .jsonPath("$.batteryExpiryDate")
        .isEqualTo("2021-02-01")
        .jsonPath("$.lastServicedDate")
        .isEqualTo("2021-02-01");
    }

    @Test
    void shouldUpdateTheLastModifiedDateEvenIfTheBeaconValuesHaveNotChanged()
      throws Exception {
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final Object updateRequestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
            .replace("replace-with-test-account-holder-id", testAccountHolderId)
        )
        .get(RegistrationUseCase.SINGLE_BEACON);

      webTestClient
        .patch()
        .uri("/registrations/register/" + beaconId)
        .bodyValue(updateRequestBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.lastModifiedDate")
        .value(not(lastModifiedDate));
    }

    @Test
    void shouldNotUpdateNonEditableBeaconFields() throws Exception {
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final Object updateRequestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
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
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final Object updateRequestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
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
        .isEqualTo(1)
        .jsonPath("$.uses[0].environment")
        .isEqualTo("AVIATION");
    }

    @Test
    void shouldReplaceTheOwner() throws Exception {
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final Object updateRequestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
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

    @Test
    void shouldReplaceTheEmergencyContacts() throws Exception {
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final Object updateRequestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
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
        .jsonPath("$.emergencyContacts[*].fullName")
        .value(hasItems("Lord Hamilton", "Father Hamilton"))
        .jsonPath("$.emergencyContacts[*].telephoneNumber")
        .value(hasItems("02392 856622", "02392 856623"))
        .jsonPath("$.emergencyContacts[*].alternativeTelephoneNumber")
        .value(hasItems("02392 856623", "02392 856624"));
    }
  }

  @Nested
  class ClaimLegacyBeacon {

    @Test
    void whenUserRegistersABeaconWithSameHexIdAndAccountHolderEmailAsLegacyBeacon_shouldClaimLegacyBeacon()
      throws Exception {
      // Setup
      TestSeeder testSeeder = new TestSeeder(webTestClient);
      final var email = UUID.randomUUID().toString() + "@test.com";
      final var legacyBeaconHexId = "1D0EA08C52FFBFF";
      final var createAccountResponseBody = testSeeder.seedAccountHolderWithEmail(
        email
      );
      final var accountHolderId = testSeeder.getAccountHolderId(
        createAccountResponseBody
      );
      testSeeder.seedLegacyBeaconWithEmailAndHexId(email, legacyBeaconHexId);

      final Object requestBody = testSeeder
        .registrationFixtureToMap(
          testSeeder
            .readFile(REGISTRATION_JSON_RESOURCE)
            .replace("replace-with-test-account-holder-id", accountHolderId)
        )
        .get(RegistrationUseCase.SINGLE_BEACON);

      // assertions
      webTestClient
        .post()
        .uri("/registrations/register")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBody)
        .exchange()
        .expectStatus()
        .isCreated();

      webTestClient
        .get()
        .uri(
          "/beacon-search/search/find-all-by-account-holder-and-email?email=" +
          email +
          "&accountHolderId=" +
          accountHolderId
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("_embedded.beaconSearch.length()")
        .isEqualTo(1)
        .jsonPath("_embedded.beaconSearch[0].hexId")
        .isEqualTo(legacyBeaconHexId)
        .jsonPath("_embedded.beaconSearch[0].beaconStatus")
        .isEqualTo("NEW");
    }
  }
}
