package uk.gov.mca.beacons.api.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
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
    final String createOwnerRequest = readFile(
      "src/test/resources/fixtures/createOwnerRequest.json"
    );
    final String createOwnerResponse = readFile(
      "src/test/resources/fixtures/createOwnerResponse.json"
    );

    webTestClient
      .post()
      .uri("/owners")
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
    final String createOwnerRequest = readFile(
      "src/test/resources/fixtures/createOwnerRequestWithDatesSpecified.json"
    );
    final String createOwnerResponse = readFile(
      "src/test/resources/fixtures/createOwnerResponseWithDatesSpecified.json"
    );

    webTestClient
      .post()
      .uri("/owners")
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
  void aGetRequestWithExistingId_shouldRespondWithExistingOwner()
    throws Exception {
    final var createdOwnerId = createNewOwner(
      readFile("src/test/resources/fixtures/createOwnerRequest.json")
    );
    final String existingOwnerResponse = readFile(
      "src/test/resources/fixtures/createOwnerResponse.json"
    );

    webTestClient
      .get()
      .uri("/owners/" + createdOwnerId)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(existingOwnerResponse);
  }

  private UUID createNewOwner(String request) throws JSONException {
    return UUID.fromString(
      new JSONObject(
        new String(
          Objects.requireNonNull(
            webTestClient
              .post()
              .uri("/owners")
              .bodyValue(request)
              .header(
                HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE
              )
              .exchange()
              .expectBody()
              .returnResult()
              .getResponseBody()
          )
        )
      )
        .getJSONObject("data")
        .getString("id")
    );
  }

  private String readFile(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)));
  }
}
