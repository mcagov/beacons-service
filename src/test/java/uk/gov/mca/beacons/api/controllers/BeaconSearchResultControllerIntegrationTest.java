package uk.gov.mca.beacons.api.controllers;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconSearchResultControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    class GetBeaconSearchResults {

        @Test
        void givenAValidRequest_shouldReturnAHttp200() {
            final String uri = "/beacon-search/search/find-all";

            webTestClient.get().uri(uri).exchange().expectStatus().is2xxSuccessful();
        }
    }
}
