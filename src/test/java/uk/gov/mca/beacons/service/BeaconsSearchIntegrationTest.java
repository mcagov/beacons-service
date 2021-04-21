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

        request.expectBody().jsonPath("$.meta.count").exists();
        //request.expectBody().jsonPath("$.meta.pageSize").exists();
        //request.expectBody().jsonPath("$.data").exists();
    }

    private WebTestClient.ResponseSpec makeGetRequest(String url) {
        return webTestClient.get().uri(url).exchange().expectStatus().is2xxSuccessful();
    }
}
