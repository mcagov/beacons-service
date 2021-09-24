package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@ExtendWith(MockitoExtension.class)
public class LegacyBeaconServiceUnitTest {

  @InjectMocks
  private LegacyBeaconService legacyBeaconService;

  @Mock
  private LegacyBeaconGateway legacyBeaconGateway;

  @Test
  void shouldReturnTheMatchingLegacyBeaconIfTheEmailAndHexIdAreTheSame() {
    // Arrange
    // Setup Beacon
    String hexId = "1D1234123412345";
    String email = "test@test.com";
    Beacon beacon = new Beacon();
    beacon.setHexId(hexId);

    // Setup LegacyBeacon
    Map<String, Object> beaconMap = new HashMap<>();
    Map<String, Object> ownerMap = new HashMap<>();
    beaconMap.put("hexId", hexId);
    ownerMap.put("email", email);
    LegacyBeacon foundLegacyBeacon = LegacyBeacon
      .builder()
      .beacon(beaconMap)
      .owner(ownerMap)
      .build();

    // Mock Gateway
    given(legacyBeaconGateway.findByHexIdAndEmail(hexId, email))
      .willReturn(foundLegacyBeacon);

    // Act
    LegacyBeacon legacyBeacon = LegacyBeaconService.match(beacon);

    // Assert
    assertThat(legacyBeacon.getHexId(), is(hexId));
    assertThat(legacyBeacon.getAssociatedEmail(), is(email));
  }
}
