package uk.gov.mca.beacons.api.registration.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import uk.gov.mca.beacons.api.WebIntegrationTest;

public class RegistrationControllerIntegrationTest extends WebIntegrationTest {

  private String accountHolderId;

  @BeforeEach
  void init() throws Exception {
    accountHolderId = seedAccountHolder();
  }

  @Nested
  class NewRegistration {

    @Test
    void shouldRegisterNewRegistration() throws Exception {
      final String registrationBody = getRegistrationBody(
        RegistrationUseCase.SINGLE_BEACON,
        accountHolderId
      );

      webTestClient
        .post()
        .uri(Endpoints.Registration.value + "/register")
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

  @Nested
  class UpdateRegistration {

    private String beaconId;

    @BeforeEach
    void init() throws Exception {
      beaconId =
        seedRegistration(RegistrationUseCase.SINGLE_BEACON, accountHolderId);
    }

    @Test
    void shouldUpdateTheBeacon() throws Exception {
      final String updateRegistrationBody = getRegistrationBody(
        RegistrationUseCase.BEACON_TO_UPDATE,
        accountHolderId
      );

      webTestClient
        .patch()
        .uri(Endpoints.Registration.value + "/register/" + beaconId)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updateRegistrationBody)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .json(updateRegistrationBody);

      webTestClient
        .get()
        .uri(Endpoints.Registration.value + "/" + beaconId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .json(updateRegistrationBody);
    }
  }

  @Nested
  class GetRegistrationByBeaconId {

    private String beaconId;

    @BeforeEach
    void init() throws Exception {
      beaconId =
        seedRegistration(RegistrationUseCase.SINGLE_BEACON, accountHolderId);
    }

    @Test
    void shouldGetTheRegistrationByBeaconId() throws Exception {
      final String registrationBody = getRegistrationBody(
        RegistrationUseCase.SINGLE_BEACON,
        accountHolderId
      );

      webTestClient
        .get()
        .uri(Endpoints.Registration.value + "/" + beaconId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .json(registrationBody);
    }
  }

  @Nested
  class GetRegistrationsByAccountHolderId {

    String firstBeaconId;
    String secondBeaconId;

    @BeforeEach
    void init() throws Exception {
      firstBeaconId =
        seedRegistration(RegistrationUseCase.SINGLE_BEACON, accountHolderId);
      secondBeaconId =
        seedRegistration(RegistrationUseCase.BEACON_TO_UPDATE, accountHolderId);
    }

    @Test
    void shouldGetTheRegistrationsByAccountHolderId() {
      webTestClient
        .get()
        .uri(
          Endpoints.Registration.value + "?accountHolderId=" + accountHolderId
        )
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$[0].id")
        .isEqualTo(secondBeaconId)
        .jsonPath("$[1].id")
        .isEqualTo(firstBeaconId);
    }
  }
}
