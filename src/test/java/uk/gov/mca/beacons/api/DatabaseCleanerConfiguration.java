package uk.gov.mca.beacons.api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class DatabaseCleanerConfiguration {

  @Bean
  public DatabaseCleaner databaseCleaner(JdbcTemplate jdbcTemplate) {
    return new DatabaseCleaner(jdbcTemplate);
  }
}
