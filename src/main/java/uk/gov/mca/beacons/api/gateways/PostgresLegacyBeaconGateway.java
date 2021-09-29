package uk.gov.mca.beacons.api.gateways;

import static java.util.stream.Collectors.toList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconEvent;
import uk.gov.mca.beacons.api.jpa.LegacyBeaconJpaRepository;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;

@Repository
@Transactional
@Slf4j
public class PostgresLegacyBeaconGateway implements LegacyBeaconGateway {

  private final LegacyBeaconJpaRepository legacyBeaconJpaRepository;
  private final LegacyBeaconMapper legacyBeaconMapper;
  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public PostgresLegacyBeaconGateway(
    LegacyBeaconJpaRepository legacyBeaconJpaRepository,
    LegacyBeaconMapper legacyBeaconMapper,
    JdbcTemplate jdbcTemplate,
    NamedParameterJdbcTemplate namedParameterJdbcTemplate
  ) {
    this.legacyBeaconJpaRepository = legacyBeaconJpaRepository;
    this.legacyBeaconMapper = legacyBeaconMapper;
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  @Override
  public LegacyBeacon save(LegacyBeacon legacyBeacon) {
    final var legacyBeaconEntity = legacyBeaconMapper.toJpaEntity(legacyBeacon);
    legacyBeaconEntity.setBeaconStatus(BeaconStatus.MIGRATED);

    log.info(
      "Saving beacon record with PK {}",
      legacyBeacon.getBeacon().get("pkBeaconId")
    );

    saveHistory(legacyBeacon);

    return legacyBeaconMapper.fromJpaEntity(
      legacyBeaconJpaRepository.save(legacyBeaconEntity)
    );
  }

  @Override
  public Optional<LegacyBeacon> findById(UUID id) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("id", id);

    try {
      LegacyBeacon legacyBeacon = namedParameterJdbcTemplate.queryForObject(
        "SELECT " +
        "id, " +
        "data->>'beacon' AS beacon, " +
        "data->>'uses' AS uses, " +
        "data->>'owner' AS owner, " +
        "data->>'secondaryOwners' AS secondary_owners, " +
        "data->>'emergencyContact' AS emergency_contact, " +
        "hex_id, " +
        "owner_email, " +
        "created_date, " +
        "last_modified_date, " +
        "beacon_status, " +
        "owner_name, " +
        "use_activities " +
        "FROM legacy_beacon " +
        "WHERE id = :id",
        paramMap,
        this::legacyBeaconRowToLegacyBeacon
      );

      addHistory(legacyBeacon);

      return Optional.ofNullable(legacyBeacon);
    } catch (EmptyResultDataAccessException e) {
      log.error("Unable to find LegacyBeacon with id {}: {}", id, e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<LegacyBeacon>> findAllByHexIdAndEmail(
    String hexId,
    String email
  ) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("owner_email", email)
      .addValue("hex_id", hexId);

    return Optional.of(
      namedParameterJdbcTemplate
        .query(
          "SELECT " +
          "id, " +
          "data->>'beacon' AS beacon, " +
          "data->>'uses' AS uses, " +
          "data->>'owner' AS owner, " +
          "data->>'secondaryOwners' AS secondary_owners, " +
          "data->>'emergencyContact' AS emergency_contact, " +
          "hex_id, " +
          "owner_email, " +
          "created_date, " +
          "last_modified_date, " +
          "beacon_status, " +
          "owner_name, " +
          "use_activities " +
          "FROM legacy_beacon " +
          "WHERE owner_email = :owner_email " +
          "AND hex_id = :hex_id",
          paramMap,
          this::legacyBeaconRowToLegacyBeacon
        )
        .stream()
        .map(this::addHistory)
        .collect(toList())
    );
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.execute("DELETE FROM legacy_beacon");
  }

  private LegacyBeacon addHistory(LegacyBeacon legacyBeacon) {
    if (legacyBeacon == null) return null;

    legacyBeacon.setHistory(findEvents(legacyBeacon));

    return legacyBeacon;
  }

  private List<LegacyBeaconEvent> findEvents(LegacyBeacon legacyBeacon) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("legacyBeaconId", legacyBeacon.getId());

    return namedParameterJdbcTemplate.query(
      "SELECT id, legacy_beacon_id, account_holder_id, claim_event_type, when_happened " +
      "FROM legacy_beacon_claim_event WHERE legacy_beacon_id = :legacyBeaconId",
      paramMap,
      (ResultSet resultSet, int rowNum) ->
        legacyBeaconClaimEventRowToLegacyBeaconEvent(
          resultSet,
          rowNum,
          legacyBeacon
        )
    );
  }

  private void saveHistory(LegacyBeacon beacon) {
    for (LegacyBeaconEvent event : beacon.getHistory()) {
      if (event instanceof LegacyBeaconClaimEvent) {
        saveLegacyBeaconClaimEvent((LegacyBeaconClaimEvent) event);
      }
    }
  }

  private void saveLegacyBeaconClaimEvent(LegacyBeaconClaimEvent event) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("id", event.getId())
      .addValue("legacyBeaconId", event.getLegacyBeacon().getId())
      .addValue("accountHolderId", event.getAccountHolderId())
      .addValue("dateTime", event.getWhenHappened());

    namedParameterJdbcTemplate.update(
      "INSERT INTO legacy_beacon_claim_event " +
      "(id, legacy_beacon_id, account_holder_id, claim_event_type, when_happened) " +
      "VALUES " +
      "(:id, :legacyBeaconId, :accountHolderId, 'claim', :dateTime)",
      paramMap
    );
  }

  private LegacyBeacon legacyBeaconRowToLegacyBeacon(
    ResultSet resultSet,
    int rowNum
  ) throws SQLException {
    return LegacyBeacon
      .builder()
      .id(UUID.fromString(resultSet.getString("id")))
      .beacon((Map<String, Object>) jsonToObject(resultSet.getString("beacon")))
      .uses(
        (List<Map<String, Object>>) jsonToObject(resultSet.getString("uses"))
      )
      .owner((Map<String, Object>) jsonToObject(resultSet.getString("owner")))
      .secondaryOwners(
        (List<Map<String, Object>>) jsonToObject(
          resultSet.getString("secondary_owners")
        )
      )
      .emergencyContact(
        (Map<String, Object>) jsonToObject(
          resultSet.getString("emergency_contact")
        )
      )
      .build();
  }

  private LegacyBeaconEvent legacyBeaconClaimEventRowToLegacyBeaconEvent(
    ResultSet resultSet,
    int rowNum,
    LegacyBeacon legacyBeacon
  ) throws SQLException {
    String claimEventType = resultSet.getString("claim_event_type");

    if (claimEventType.equals("claim")) {
      return LegacyBeaconClaimEvent
        .builder()
        .id(UUID.fromString(resultSet.getString("id")))
        .whenHappened(
          resultSet.getObject("when_happened", OffsetDateTime.class)
        )
        .legacyBeacon(legacyBeacon)
        .accountHolderId(
          UUID.fromString(resultSet.getString("account_holder_id"))
        )
        .build();
    }

    return null;
  }

  private Object jsonToObject(String json) {
    try {
      return new ObjectMapper().readValue(json, new TypeReference<>() {});
    } catch (Exception e) {
      log.error("Error reading json:" + e);
      return null;
    }
  }
}
