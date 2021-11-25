package uk.gov.mca.beacons.api;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

public class DatabaseCleaner {

  JdbcTemplate jdbcTemplate;

  public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void clean() {
    JdbcTestUtils.deleteFromTables(
      jdbcTemplate,
      "account_holder",
      "beacon",
      "beacon_use",
      "legacy_beacon",
      "legacy_beacon_claim_event",
      "note",
      "person"
    );
  }

  public void clean(String... tableNames) {
    JdbcTestUtils.deleteFromTables(jdbcTemplate, tableNames);
  }
}
