package uk.gov.mca.beacons.api.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class OwnerControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void aNewValidOwnerPostRequest_ShouldCreateTheOwner() throws Exception {
        final String createOwnerRequest = new String(
                Files.readAllBytes(
                        Paths.get("src/test/resources/fixtures/createOwnerRequest.json")
                )
        );
        final String createOwnerResponse = new String(
                Files.readAllBytes(
                        Paths.get("src/test/resources/fixtures/createOwnerResponse.json")
                )
        );

        webTestClient
                .post()
                .uri("/owner")
                .bodyValue(createOwnerRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json(createOwnerResponse)
                .jsonPath("$.data.id")
                .isNotEmpty()
                .jsonPath("$.data.attributes.createdDate")
                .isNotEmpty()
                .jsonPath("$.data.attributes.lastModifiedDate")
                .isNotEmpty();
    }

    @Test
    void aNewValidOwnerPostRequest_shouldCreateTheOwnerWithCreatedDatesAndLastModifiedDates()
            throws Exception {
        final String createOwnerRequest = new String(
                Files.readAllBytes(
                        Paths.get(
                                "src/test/resources/fixtures/createOwnerRequestWithDatesSpecified.json"
                        )
                )
        );
        final String createOwnerResponse = new String(
                Files.readAllBytes(
                        Paths.get(
                                "src/test/resources/fixtures/createOwnerResponseWithDatesSpecified.json"
                        )
                )
        );

        webTestClient
                .post()
                .uri("/owner")
                .bodyValue(createOwnerRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json(createOwnerResponse)
                .jsonPath("$.data.id")
                .isNotEmpty();
    }

    @Test
    void aGetRequestWithExistingId_shouldRespondWithExistingOwner() throws Exception {
        final String createOwnerRequest = new String(
                Files.readAllBytes(
                        Paths.get("src/test/resources/fixtures/createOwnerRequest.json")
                )
        );
        final String createdOwnerId = webTestClient
                .post()
                .uri("/owner")
                .bodyValue(createOwnerRequest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody()
                .jsonPath("$.data.id").toString();
        final String existingOwnerResponse = new String(
                Files.readAllBytes(
                        Paths.get("src/test/resources/fixtures/createOwnerResponse.json")
                )
        );

        webTestClient
                .get()
                .uri("/owner/" + createdOwnerId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(existingOwnerResponse);
    }
}
