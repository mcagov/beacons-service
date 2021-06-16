package uk.gov.mca.beacons.service.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AccountHolderControllerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AccountHolderRepository accountHolderRepository;

    @Test
    void requestCreateAccountHolder_shouldRespondWithTheCreatedResource() throws Exception {
        String newAccountHolderRequest = new String(
                Files.readAllBytes(Paths.get("src/test/resources/fixtures/createAccountHolderRequest.json"))
        );
        String expectedResponse = new String(
                Files.readAllBytes(Paths.get("src/test/resources/fixtures/createAccountHolderResponse.json"))
        );
        AccountHolder accountHolder = new AccountHolder();
        given(accountHolderRepository.save(any(AccountHolder.class))).willReturn(accountHolder);

        webTestClient.post()
                .uri("/account-holder")
                .body(BodyInserters.fromValue(newAccountHolderRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange().expectBody().json(expectedResponse);
    }
}
