package uk.gov.mca.beacons.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MigrationControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Nested
  class CreateMigration {

    @Test
    void shouldRespondWithTheCreatedResource() throws Exception {
      String testAuthId = UUID.randomUUID().toString();
      String newAccountHolderRequest = readFile(
              "src/test/resources/fixtures/createMigrationRequest.json"
      )
              .replace("replace-with-test-auth-id", testAuthId);
      String expectedResponse = readFile(
              "src/test/resources/fixtures/createMigrationResponse.json"
      )
              .replace("replace-with-test-auth-id", testAuthId);

      var response = webTestClient
              .post()
              .uri("/migration")
              .body(BodyInserters.fromValue(newAccountHolderRequest))
              .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .exchange()
              .expectStatus()
              .isCreated();

      response.json(expectedResponse);
      response.jsonPath("$.data.id").isNotEmpty();
    }
  }

  private String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }
}
