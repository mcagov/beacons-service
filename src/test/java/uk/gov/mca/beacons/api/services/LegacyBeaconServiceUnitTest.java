package uk.gov.mca.beacons.api.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;

@ExtendWith(MockitoExtension.class)
public class LegacyBeaconServiceUnitTest {

  @InjectMocks
  private LegacyBeaconGateway legacyBeaconGateway;

  @InjectMocks
  private EventGateway EventGateway;

  @Nested
  class Claim {

    @Test
    void shouldCreateANewLegacyBeaconClaimEvent() {
      var legacyBeacon = LegacyBeacon.builder().id(UUID.randomUUID());

      legacyBeaconGateway.claim(legacyBeacon);

      verify(eventGateway, times(1)).save(legacyBeaconClaimEvent);
    }
  }
}
