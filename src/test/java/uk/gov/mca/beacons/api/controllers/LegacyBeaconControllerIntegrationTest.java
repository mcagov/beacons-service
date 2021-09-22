package uk.gov.mca.beacons.api.controllers;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.gateways.UserGateway;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class LegacyBeaconControllerIntegrationTest {

    private User user;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserGateway userGateway;

    @BeforeEach
    void init() {
        final var userId = UUID.randomUUID();

        user =
                AccountHolder
                        .builder()
                        .id(userId)
                        .email("beacon@beacons.com")
                        .fullName("Mr. Beacon")
                        .build();
    }

    @Test
    void shouldReturnTheLegacyBeaconById() throws Exception {
        final String legacyBeaconId = getLegacyBeaconId(seedLegacyBeacon());
        final var createLegacyBeaconResponse = Files.readString(
                Paths.get("src/test/resources/fixtures/createLegacyBeaconResponse.json")
        );

        webTestClient
                .get()
                .uri("/legacy-beacon/" + legacyBeaconId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(createLegacyBeaconResponse)
                .jsonPath("$.data.id")
                .isEqualTo(legacyBeaconId);
    }

    @Test
    void shouldReturnHttp404IfTheLegacyBeaconCannotBeFound() {
        final var legacyBeaconId = UUID.randomUUID().toString();

        webTestClient
                .get()
                .uri("/legacy-beacon/" + legacyBeaconId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    private WebTestClient.BodyContentSpec seedLegacyBeacon() throws Exception {
        final var createLegacyBeaconRequest = Files.readString(
                Paths.get("src/test/resources/fixtures/createLegacyBeaconRequest.json")
        );

        return webTestClient
                .post()
                .uri("/migrate/legacy-beacon")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createLegacyBeaconRequest)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody();
    }

    private String getLegacyBeaconId(
            WebTestClient.BodyContentSpec createLegacyBeaconResponse
    ) throws Exception {
        return new ObjectMapper()
                .readValue(
                        createLegacyBeaconResponse.returnResult().getResponseBody(),
                        ObjectNode.class
                )
                .get("data")
                .get("id")
                .textValue();
    }

    private String getLegacyBeaconHexId(
            WebTestClient.BodyContentSpec createLegacyBeaconResponse
    ) throws Exception {
        return new ObjectMapper()
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

    private String getLegacyBeaconEmail(
            WebTestClient.BodyContentSpec createLegacyBeaconResponse
    ) throws Exception {
        return new ObjectMapper()
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

    private void deleteLegacyBeacon(
            String legacyBeaconId,
            String userId,
            String reason
    ) throws Exception {
        final var deleteLegacyBeaconRequest = Files
                .readString(
                        Paths.get("src/test/resources/fixtures/deleteLegacyBeaconRequest.json")
                )
                .replace("replace-with-test-legacy-beacon-id", legacyBeaconId)
                .replace("replace-with-test-user-id", userId)
                .replace("replace-with-test-reason", reason);

        given(userGateway.getUserById(user.getId())).willReturn(user);

        webTestClient
                .patch()
                .uri("/legacy-beacon/" + legacyBeaconId + "/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deleteLegacyBeaconRequest)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Nested
    class ClaimEvent {

        @Test
        void whenTheUserHitsTheClaimEndpoint_aNewRegistrationIsCreatedWithTheNecessaryProperties()
                throws Exception {
            var legacyBeaconResponse = seedLegacyBeacon();
            var legacyBeaconId = getLegacyBeaconId(legacyBeaconResponse);
            var legacyBeaconHexId = getLegacyBeaconHexId(legacyBeaconResponse);
            var legacyBeaconEmail = getLegacyBeaconEmail(legacyBeaconResponse);
            seedAccountHolderWithEmail(legacyBeaconEmail);

            webTestClient
                    .post()
                    .uri("legacy-beacon/" + legacyBeaconId + "/claim")
                    .exchange()
                    .expectStatus()
                    .isOk();

            webTestClient
                    .get()
                    .uri(
                            "beacon-search/search/find-all-by-account-holder-and-email?email=" +
                                    user.getEmail() +
                                    "&accountHolderId=" +
                                    user.getId().toString()
                    )
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody()
                    .jsonPath("$.data[0].type")
                    .isEqualTo("beacon")
                    .jsonPath("$.data[0].attributes.hexId")
                    .isEqualTo(legacyBeaconHexId)
                    .jsonPath("$.data[0].attributes.status")
                    .isEqualTo("NEW");
        }

        public void seedAccountHolderWithEmail(String email) throws Exception {
            String newAccountHolderRequest = readFile(
                    "src/test/resources/fixtures/createAccountHolderRequest.json"
            ).replace("testy@mctestface.com", email);

            webTestClient
                    .post()
                    .uri("/account-holder")
                    .body(BodyInserters.fromValue(newAccountHolderRequest))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .exchange()
                    .expectBody();
        }

        private String readFile(String filePath) throws IOException {
            return Files.readString(Paths.get(filePath));
        }
    }
}
