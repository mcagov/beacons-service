package uk.gov.mca.beacons.api.gateways;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.jpa.LegacyBeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;

@Repository
@Transactional
@Slf4j
public class LegacyBeaconGatewayImpl implements LegacyBeaconGateway {

  private final LegacyBeaconJpaRepository legacyBeaconJpaRepository;
  private final LegacyBeaconMapper legacyBeaconMapper;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public LegacyBeaconGatewayImpl(
    LegacyBeaconJpaRepository legacyBeaconJpaRepository,
    LegacyBeaconMapper legacyBeaconMapper,
    NamedParameterJdbcTemplate namedParameterJdbcTemplate,
    JdbcTemplate jdbcTemplate
  ) {
    this.legacyBeaconJpaRepository = legacyBeaconJpaRepository;
    this.legacyBeaconMapper = legacyBeaconMapper;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public LegacyBeacon save(LegacyBeacon beacon) {
    final var legacyBeaconEntity = legacyBeaconMapper.toJpaEntity(beacon);
    legacyBeaconEntity.setBeaconStatus(BeaconStatus.MIGRATED);

    log.info(
      "Saving beacon record with PK {}",
      beacon.getBeacon().get("pkBeaconId")
    );
    return legacyBeaconMapper.fromJpaEntity(
      legacyBeaconJpaRepository.save(legacyBeaconEntity)
    );
  }

  @Override
  public void deleteAll() {
    jdbcTemplate.execute("DELETE FROM legacy_beacon");
  }

  @Override
  public Optional<LegacyBeacon> findById(UUID id) {
    return legacyBeaconJpaRepository
      .findById(id)
      .map(legacyBeaconMapper::fromJpaEntity);
  }

  @Override
  public List<LegacyBeacon> findAllByHexIdAndEmail(String hexId, String email) {
    final String sql =
      "SELECT " +
      "id, hex_id, owner_email, use_activities, owner_name, created_date, last_modified_date, beacon_status, data FROM legacy_beacon WHERE owner_email = :email" +
      " AND hex_id = :hexId";

    List<LegacyBeaconEntity> legacyBeaconEntities = jdbcTemplate.query(
      sql,
      new PreparedStatementSetter() {
        public void setValues(PreparedStatement preparedStatement)
          throws SQLException {
          preparedStatement.setString(0, email);
          preparedStatement.setString(1, hexId);
        }
      },
      this::mapRow
    );

    return legacyBeaconEntities
      .stream()
      .map(legacyBeaconMapper::fromJpaEntity)
      .collect(Collectors.toList());
  }

  private LegacyBeaconEntity mapRow(ResultSet resultSet, int rowNum) {
    LegacyBeaconEntity legacyBeaconEntity = new LegacyBeaconEntity();

    try {
      legacyBeaconEntity.setId(UUID.fromString(resultSet.getString("id")));
      legacyBeaconEntity.setHexId(resultSet.getString("hex_id"));
      legacyBeaconEntity.setOwnerEmail(resultSet.getString("owner_email"));
      legacyBeaconEntity.setUseActivities(
        resultSet.getString("use_activities")
      );
      legacyBeaconEntity.setOwnerName(resultSet.getString("owner_name"));
      legacyBeaconEntity.setData(dataColumnToMap(resultSet.getString("data")));
    } catch (Exception e) {
      log.error(String.valueOf(e));
    }
    return legacyBeaconEntity;
  }

  private Map<String, Object> dataColumnToMap(String contentsOfJsonBDataColumn)
    throws JsonProcessingException {
    ObjectMapper dataColumnMapper = new ObjectMapper();

    return dataColumnMapper.readValue(
      contentsOfJsonBDataColumn,
      new TypeReference<>() {}
    );
  }
}
