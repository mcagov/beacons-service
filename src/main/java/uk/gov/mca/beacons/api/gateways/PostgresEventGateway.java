package uk.gov.mca.beacons.api.gateways;

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
        final SqlParameterSource eventParamMap = new MapSqlParameterSource()
                .addValue("legacyBeaconId", legacyBeaconClaimEvent.getLegacyBeacon().getId())
                .addValue("accountHolderId", legacyBeaconClaimEvent.getAccountHolder().getId())
                .addValue("dateTime", legacyBeaconClaimEvent.getDateTime());

        jdbcTemplate.update("INSERT INTO claim_event " +
                        "(legacyBeaconId, accountHolderId, dateTime) " +
                        "VALUES " +
                        "(:legacyBeaconId, :accountHolderId, :dateTime)",
                eventParamMap);
    }
}
