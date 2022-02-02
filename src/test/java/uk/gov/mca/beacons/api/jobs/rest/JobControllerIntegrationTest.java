package uk.gov.mca.beacons.api.jobs.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.jayway.jsonpath.JsonPath;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
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
  private RestHighLevelClient restHighLevelClient;

  @AfterEach
  void cleanContext() {
    jobRepositoryTestUtils.removeJobExecutions();
  }

  @Test
  void givenBeacons_whenJobIsTriggeredViaEndpoint_ShouldWriteBeaconSearchDocumentsToOpensearch()
    throws Exception {
    // given
    String accountHolderId_1 = seedAccountHolder();
    String accountHolderId_2 = seedAccountHolder();
    String seededRegistrationId_1 = seedRegistration(RegistrationUseCase.SINGLE_BEACON, accountHolderId_1);
    String seededRegistrationId_2 = seedRegistration(RegistrationUseCase.SINGLE_BEACON, accountHolderId_2);

    // when
    webTestClient
      .post()
      .uri(Endpoints.Job.value + "/reindexSearch")
      .exchange()
      .expectStatus()
      .isAccepted()
      .expectBody()
      .jsonPath("$.location")
      .isEqualTo("/spring-api/job/reindexSearch/1");

    pollJobStatusUntilCompletedOrFailed(
      Endpoints.Job.value + "/reindexSearch/1"
    );

    // then
    SearchRequest searchRequest = new SearchRequest("beacon_search");
    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.query(QueryBuilders.matchAllQuery()).sort(new FieldSortBuilder("lastModifiedDate").order(SortOrder.ASC));
    searchRequest.source(searchSourceBuilder);

    var response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

    assertThat(response.getHits().getAt(0).getId(), is(seededRegistrationId_1));
    assertThat(response.getHits().getAt(1).getId(), is(seededRegistrationId_2));
  }

  private void pollJobStatusUntilCompletedOrFailed(String endpoint) {
    Flux
      .just(webTestClient.get().uri(endpoint).exchange())
      .flatMap(
        clientResponse -> {
          String status = JsonPath.read(
            clientResponse
              .returnResult(String.class)
              .getResponseBody()
              .blockFirst(),
            "$.status"
          );

          assertThat(status, not("FAILED"));

          if (!status.equals("COMPLETED")) {
            return Mono.error(new RuntimeException());
          }
          return Mono.just(status);
        }
      )
      .retryWhen(Retry.fixedDelay(10, Duration.ofSeconds(1)))
      .blockFirst();
  }
}
