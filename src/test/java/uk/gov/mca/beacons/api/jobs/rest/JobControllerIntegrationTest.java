package uk.gov.mca.beacons.api.jobs.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.jayway.jsonpath.JsonPath;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import uk.gov.mca.beacons.api.WebIntegrationTest;
import uk.gov.mca.beacons.api.search.documents.BeaconSearchDocument;
import uk.gov.mca.beacons.api.search.repositories.BeaconSearchRepository;

@SpringBatchTest
public class JobControllerIntegrationTest extends WebIntegrationTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private JobRepositoryTestUtils jobRepositoryTestUtils;

  @Autowired
  private BeaconSearchRepository beaconSearchRepository;

  @AfterEach
  void cleanContext() {
    jobRepositoryTestUtils.removeJobExecutions();
  }

  @Test
  void whenJobIsTriggeredViaEndpoint_ShouldWriteBeaconSearchDocumentsToOpensearch()
    throws Exception {
    // given
    String accountHolderId_1 = seedAccountHolder();
    String accountHolderId_2 = seedAccountHolder();
    seedRegistration(RegistrationUseCase.SINGLE_BEACON, accountHolderId_1);
    seedRegistration(RegistrationUseCase.SINGLE_BEACON, accountHolderId_2);

    webTestClient
      .post()
      .uri(Endpoints.Job.value + "/reindexSearch")
      .exchange()
      .expectStatus()
      .isAccepted()
      .expectBody()
      .jsonPath("$.Location")
      .isEqualTo("/spring-api/job/reindexSearch/1");

    pollJobStatusUntilCompletedOrFailed(
      Endpoints.Job.value + "/reindexSearch/1"
    );

    List<BeaconSearchDocument> beaconSearchDocuments = new ArrayList<>();
    beaconSearchRepository
      .findAll()
      .iterator()
      .forEachRemaining(beaconSearchDocuments::add);

    assertThat(beaconSearchDocuments.size(), is(2));
  }

  private void pollJobStatusUntilCompletedOrFailed(String endpoint) {
    Flux
      .just(webTestClient.get().uri(endpoint).exchange())
      .flatMap(
        clientResponse -> {
          String status = JsonPath.read(
            clientResponse.returnResult(String.class).getResponseBody(),
            "$.Status"
          );

          assertThat(status, not("FAILED"));

          if (!status.equals("COMPLETED")) {
            return Mono.error(new RuntimeException());
          }
          return null;
        }
      )
      .retryWhen(Retry.backoff(10, Duration.ofSeconds(1)))
      .blockFirst();
  }
}
