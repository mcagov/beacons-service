package uk.gov.mca.beacons.api.services.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@Slf4j
public class LegacyBeaconSearchScheduler {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public LegacyBeaconSearchScheduler(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Scheduled(fixedRateString = "${beacons.scheduled.legacy-beacon-search-view}")
  public void refreshView() {
    log.debug("Refreshing beacon search view");

    final SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
      .withFunctionName("refresh_legacy_beacon_search_schedule");
    simpleJdbcCall.execute();
    log.debug("Finished refreshing beacon search view");
  }
}
