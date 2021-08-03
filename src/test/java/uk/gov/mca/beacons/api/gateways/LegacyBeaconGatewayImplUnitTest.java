package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

  @Test
  void save_shouldSetTheStatusOfTheLegacyBeaconToMigrated(
    @Mock LegacyBeaconEntity legacyBeaconEntity
  ) {
    final var legacyBeacon = new LegacyBeacon();
    given(legacyBeaconMapper.toJpaEntity(legacyBeacon))
      .willReturn(legacyBeaconEntity);

    legacyBeaconGateway.save(legacyBeacon);

    verify(legacyBeaconEntity, times(1)).setBeaconStatus(BeaconStatus.MIGRATED);
  }
}
