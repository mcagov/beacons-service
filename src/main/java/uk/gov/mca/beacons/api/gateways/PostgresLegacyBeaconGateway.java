package uk.gov.mca.beacons.api.gateways;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;
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

  private void saveHistory(LegacyBeacon beacon) {
    for (LegacyBeaconEvent event : beacon.getHistory()) {
      saveEvent(event);
    }
  }

  private void saveEvent(LegacyBeaconEvent legacyBeaconEvent) {
    if (legacyBeaconEvent instanceof LegacyBeaconClaimEvent) {
      saveLegacyBeaconClaimEvent((LegacyBeaconClaimEvent) legacyBeaconEvent);
    }
  }

  private void saveLegacyBeaconClaimEvent(LegacyBeaconClaimEvent event) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("id", event.getId())
      .addValue("legacyBeaconId", event.getLegacyBeacon().getId())
      .addValue("accountHolderId", event.getAccountHolder().getId())
      .addValue("dateTime", event.getWhenHappened());

    namedParameterJdbcTemplate.update(
      "INSERT INTO legacy_beacon_claim_event " +
      "(id, legacy_beacon_id, account_holder_id, claim_event_type, when_happened) " +
      "VALUES " +
      "(:id, :legacyBeaconId, :accountHolderId, 'claim', :dateTime)",
      paramMap
    );
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.execute("DELETE FROM legacy_beacon");
  }

  @Override
  public Optional<LegacyBeacon> findById(UUID id) {
    final SqlParameterSource paramMap = new MapSqlParameterSource()
      .addValue("id", id);

    try {
      LegacyBeacon legacyBeacon = namedParameterJdbcTemplate.queryForObject(
        "SELECT id, data->>'beacon' AS beacon, data->>'uses' AS uses, data->>'owner' AS owner, " +
        "data->>'secondaryOwners' AS secondary_owners, data->>'emergencyContact' AS emergency_contact, " +
        "hex_id, owner_email, created_date, last_modified_date, beacon_status, owner_name, use_activities " +
        "FROM legacy_beacon " +
        "WHERE id = :id",
        paramMap,
        this::rowToLegacyBeacon
      );
      return Optional.of(legacyBeacon);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<LegacyBeacon> findAllByHexIdAndEmail(String hexId, String email) {
    final String sql =
      "SELECT " +
      "id, " +
      "hex_id, " +
      "owner_email, " +
      "use_activities, " +
      "owner_name, " +
      "created_date, " +
      "last_modified_date, " +
      "beacon_status, " +
      "data " +
      "FROM legacy_beacon WHERE owner_email = ? " +
      "AND hex_id = ?";

    List<LegacyBeaconEntity> legacyBeaconEntities = jdbcTemplate.query(
      sql,
      preparedStatement -> {
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, hexId);
      },
      this::mapRow
    );

    return legacyBeaconEntities
      .stream()
      .map(legacyBeaconMapper::fromJpaEntity)
      .collect(Collectors.toList());
  }

  private LegacyBeacon rowToLegacyBeacon(ResultSet resultSet, int rowNum)
    throws SQLException {
    ObjectMapper objectMapper = new ObjectMapper();

    UUID id = UUID.fromString(resultSet.getString("id"));
    Map<String, Object> beacon = (Map<String, Object>) jsonToMap(
      resultSet.getString("beacon")
    );
    List<Map<String, Object>> uses = (List<Map<String, Object>>) jsonToMap(
      resultSet.getString("uses")
    );
    Map<String, Object> owner = (Map<String, Object>) jsonToMap(
      resultSet.getString("owner")
    );
    List<Map<String, Object>> secondaryOwners = (List<Map<String, Object>>) jsonToMap(
      resultSet.getString("secondary_owners")
    );
    Map<String, Object> emergencyContact = (Map<String, Object>) jsonToMap(
      resultSet.getString("emergency_contact")
    );

    return LegacyBeacon
      .builder()
      .id(id)
      .beacon(beacon)
      .uses(uses)
      .owner(owner)
      .secondaryOwners(secondaryOwners)
      .emergencyContact(emergencyContact)
      .build();
  }

  private Object jsonToMap(String json) {
    ObjectMapper dataColumnMapper = new ObjectMapper();

    try {
      return dataColumnMapper.readValue(json, new TypeReference<>() {});
    } catch (Exception e) {
      log.error(
        "Error reading value of 'legacy_beacon' table column 'data': " + e
      );
    }

    return null;
  }

  private LegacyBeaconEntity mapRow(ResultSet resultSet, int rowNum)
    throws SQLException {
    LegacyBeaconEntity legacyBeaconEntity = new LegacyBeaconEntity();

    legacyBeaconEntity.setId(UUID.fromString(resultSet.getString("id")));
    legacyBeaconEntity.setHexId(resultSet.getString("hex_id"));
    legacyBeaconEntity.setOwnerEmail(resultSet.getString("owner_email"));
    legacyBeaconEntity.setUseActivities(resultSet.getString("use_activities"));
    legacyBeaconEntity.setOwnerName(resultSet.getString("owner_name"));
    legacyBeaconEntity.setData(dataColumnToMap(resultSet.getString("data")));

    return legacyBeaconEntity;
  }

  private Map<String, Object> dataColumnToMap(
    String contentsOfJsonBDataColumn
  ) {
    ObjectMapper dataColumnMapper = new ObjectMapper();

    try {
      return dataColumnMapper.readValue(
        contentsOfJsonBDataColumn,
        new TypeReference<>() {}
      );
    } catch (Exception e) {
      log.error(
        "Error reading value of 'legacy_beacon' table column 'data': " + e
      );
    }

    return null;
  }
}
