package uk.gov.mca.beacons.api.gateways;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;

public class PostgresEventGateway implements EventGateway {

  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public PostgresEventGateway(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void save(LegacyBeaconClaimEvent legacyBeaconClaimEvent)
    throws SQLException {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("id", legacyBeaconClaimEvent.getId())
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
      "INSERT INTO claim_event (" +
      "id, legacy_beacon_id, account_holder_id, type, date_time " +
      "VALUES " +
      "(:id, :legacyBeaconId, :accountHolderId, 'claim', :dateTime)",
      paramMap
    );
  }
}
