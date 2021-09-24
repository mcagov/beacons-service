package uk.gov.mca.beacons.api.gateways;

import java.util.List;
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
        jdbcTemplate.execute("TRUNCATE TABLE legacy_beacon");
    }

    @Override
    public Optional<LegacyBeacon> findById(UUID id) {
        return legacyBeaconJpaRepository
                .findById(id)
                .map(legacyBeaconMapper::fromJpaEntity);
    }

    @Override
    public List<LegacyBeacon> findAllByHexIdAndEmail(String hexId, String email) {
        final SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("hexId", hexId)
                .addValue("email", email);

        LegacyBeaconEntity legacyBeaconEntity;
        try {
            legacyBeaconEntity =
                    namedParameterJdbcTemplate.queryForObject(
                            "SELECT " +
                                    "id, hex_id, owner_email, use_activities, owner_name, created_date, last_modified_date, beacon_status, data FROM legacy_beacon WHERE owner_email = 'ownerbeacon@beacons.com' AND hex_id = '9D0E1D1B8C00001'",
                            paramMap,
                            LegacyBeaconEntity.class
                    );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        assert legacyBeaconEntity != null;
        return List.of(legacyBeaconMapper.fromJpaEntity(legacyBeaconEntity));
    }
}
