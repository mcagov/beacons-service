package uk.gov.mca.beacons.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient

public class BeaconsSearchIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void requestAllBeaconControllerShouldReturnSomeBeacons() {
        var request = makeGetRequest("/beacons/");

        request.jsonPath("$.meta.pageSize").exists();
        request.jsonPath("$.meta.count").exists();
        request.jsonPath("$.data").exists();
    }

    private WebTestClient.BodyContentSpec makeGetRequest(String url) {
        return webTestClient.get().uri(url).exchange()
        .expectStatus().is2xxSuccessful().expectBody();
    }
}
