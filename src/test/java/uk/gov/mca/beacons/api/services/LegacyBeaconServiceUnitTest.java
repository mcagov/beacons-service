package uk.gov.mca.beacons.api.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

    @Nested
    class Claim {

        @Test
        void shouldCreateANewLegacyBeaconClaimEvent() {
            Map<String, Object> owner = new HashMap<>();
            owner.put("email", "steve@apple.com");
            var legacyBeacon = LegacyBeacon.builder().id(UUID.randomUUID()).owner(owner).build();

            legacyBeaconService.claim(legacyBeacon);

            verify(eventGateway, times(1)).save(any(LegacyBeaconClaimEvent.class
            ));
        }
    }
}
