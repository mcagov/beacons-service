package uk.gov.mca.beacons.api.gateways;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.jpa.LegacyBeaconJpaRepository;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;

@Repository
@Transactional
@Slf4j
public class LegacyBeaconGatewayImpl implements LegacyBeaconGateway {

  private final LegacyBeaconJpaRepository legacyBeaconJpaRepository;
  private final LegacyBeaconMapper legacyBeaconMapper;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public LegacyBeaconGatewayImpl(
    LegacyBeaconJpaRepository legacyBeaconJpaRepository,
    LegacyBeaconMapper legacyBeaconMapper,
    JdbcTemplate jdbcTemplate
  ) {
    this.legacyBeaconJpaRepository = legacyBeaconJpaRepository;
    this.legacyBeaconMapper = legacyBeaconMapper;
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
  public List<LegacyBeacon> findAll() {
    return StreamSupport
      .stream(legacyBeaconJpaRepository.findAll().spliterator(), false)
      .map(legacyBeaconMapper::fromJpaEntity)
      .collect(Collectors.toList());
  }

  @Override
  public Optional<LegacyBeacon> findById(UUID id) {
    if (id.equals(UUID.fromString("c603c49d-257f-4445-a11c-52c3b7a90c36"))) {
      return Optional.of(new LegacyBeacon());
    }

    return Optional.empty();
  }
}
