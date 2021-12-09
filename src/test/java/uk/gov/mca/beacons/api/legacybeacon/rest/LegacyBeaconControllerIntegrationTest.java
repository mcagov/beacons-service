package uk.gov.mca.beacons.api.legacybeacon.rest;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import uk.gov.mca.beacons.api.BaseIntegrationTest;

@AutoConfigureWebTestClient
public class LegacyBeaconControllerIntegrationTest extends BaseIntegrationTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  void shouldFindTheLegacyBeaconById() throws Exception {
    final String createLegacyBeaconResponse = fixtureHelper.getFixture(
      "src/test/resources/fixtures/createLegacyBeaconResponse.json"
    );

    final String legacyBeaconId = createLegacyBeacon();

    webTestClient
      .get()
      .uri("/spring-api/legacy-beaconv2/" + legacyBeaconId)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody()
      .json(createLegacyBeaconResponse);
  }

  private String createLegacyBeacon() throws Exception {
    String createLegacyBeaconRequest = fixtureHelper.getFixture(
      "src/test/resources/fixtures/createLegacyBeaconRequest.json"
    );

    final String responseBody = webTestClient
      .post()
      .uri("/spring-api/migratev2/legacy-beacon")
      .body(BodyInserters.fromValue(createLegacyBeaconRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .returnResult(String.class)
      .getResponseBody()
      .blockFirst();

    return JsonPath.read(responseBody, "$.data.id");
  }
}
