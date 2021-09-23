package uk.gov.mca.beacons.api.gateways;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;

@Repository
public class PostgresEventGateway implements EventGateway {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public PostgresEventGateway(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void save(LegacyBeaconClaimEvent legacyBeaconClaimEvent) {
    final var claimEventId = UUID.randomUUID();
    final SqlParameterSource eventParamMap = new MapSqlParameterSource()
      .addValue("id", claimEventId)
      .addValue(
        "legacyBeaconId",
        legacyBeaconClaimEvent.getLegacyBeacon().getId()
      )
      .addValue(
        "accountHolderId",
        legacyBeaconClaimEvent.getAccountHolder().getId()
      )
      .addValue("dateTime", legacyBeaconClaimEvent.getDateTime());

    jdbcTemplate.update(
      "INSERT INTO claim_event " +
      "(id, legacy_beacon_id, account_holder_id, date_time, type) " +
      "VALUES " +
      "(:id, :legacyBeaconId, :accountHolderId, :dateTime, 'claim')",
      eventParamMap
    );
  }
}
