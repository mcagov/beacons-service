package uk.gov.mca.beacons.api.gateways;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.jpa.LegacyBeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.LegacyBeaconEntity;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;

@ExtendWith(MockitoExtension.class)
class LegacyBeaconGatewayImplUnitTest {

  @InjectMocks
  private LegacyBeaconGatewayImpl legacyBeaconGateway;

  @Mock
  private LegacyBeaconJpaRepository legacyBeaconJpaRepository;

  @Mock
  private LegacyBeaconMapper legacyBeaconMapper;

  @Mock
  private JdbcTemplate jdbcTemplate;

  @Test
  void save_shouldSetTheStatusOfTheLegacyBeaconToMigrated(
    @Mock LegacyBeaconEntity legacyBeaconEntity
  ) {
    final var legacyBeacon = LegacyBeacon
      .builder()
      .beacon(Map.of("pkBeaconId", 1))
      .build();
    given(legacyBeaconMapper.toJpaEntity(legacyBeacon))
      .willReturn(legacyBeaconEntity);

    legacyBeaconGateway.save(legacyBeacon);

    verify(legacyBeaconEntity, times(1)).setBeaconStatus(BeaconStatus.MIGRATED);
  }

  @Test
  void deleteAll_shouldDeleteAllLegacyRecordsThroughJdbc() {
    legacyBeaconGateway.deleteAll();

    verify(jdbcTemplate, times(1)).execute("TRUNCATE TABLE legacy_beacon");
  }

  @Test
  void findById_shouldNotCallThroughToTheMapperIfTheBeaconCannotBeFound() {
    final var id = UUID.randomUUID();

    legacyBeaconGateway.findById(id);

    verify(legacyBeaconMapper, times(0)).fromJpaEntity(any());
  }

  @Test
  void findById_shouldMapTheEntityIfItExists() {
    final var id = UUID.randomUUID();
    given(legacyBeaconJpaRepository.findById(id))
      .willReturn(Optional.of(new LegacyBeaconEntity()));
    given(legacyBeaconMapper.fromJpaEntity(any()))
      .willReturn(new LegacyBeacon());

    final var legacyBeacon = legacyBeaconGateway.findById(id);

    verify(legacyBeaconMapper, times(1)).fromJpaEntity(any());
    assertTrue(legacyBeacon.isPresent());
  }
}
