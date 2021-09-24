package uk.gov.mca.beacons.api.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.EventGateway;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.mappers.LegacyBeaconMapper;

@ExtendWith(MockitoExtension.class)
public class LegacyBeaconServiceUnitTest {

  @InjectMocks
  private LegacyBeaconService legacyBeaconService;

  @Mock
  private LegacyBeaconGateway legacyBeaconGateway;

  @Mock
  private EventGateway eventGateway;

  @Mock
  private AccountHolderGateway accountHolderGateway;

  @Mock
  CreateRegistrationService createRegistrationService;

  @Mock
  LegacyBeaconMapper legacyBeaconMapper;

  @Nested
  class Claim {

    private LegacyBeacon legacyBeacon;

    @BeforeEach
    void init() {
      Map<String, Object> owner = new HashMap<>();
      owner.put("email", "steve@apple.com");

      legacyBeacon =
        LegacyBeacon.builder().id(UUID.randomUUID()).owner(owner).build();
    }

    @Test
    void shouldCreateANewLegacyBeaconClaimEvent() {
      legacyBeaconService.claim(legacyBeacon);

      verify(eventGateway, times(1)).save(any(LegacyBeaconClaimEvent.class));
    }
  }
}
