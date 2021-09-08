package uk.gov.mca.beacons.api.services.scheduled;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class LegacyBeaconSearchSchedulerIntegrationTest {

  @Autowired
  private LegacyBeaconSearchScheduler scheduler;

  @Test
  void shouldRefreshTheView() {
    scheduler.refreshView();
  }
}
