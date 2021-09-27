package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.EventGateway;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@ExtendWith(MockitoExtension.class)
public class LegacyBeaconServiceUnitTest {

  @InjectMocks
  private LegacyBeaconService legacyBeaconService;

  @Mock
  private LegacyBeaconGateway legacyBeaconGateway;

  @Mock
  private AccountHolderService accountHolderService;

  @Mock
  private AccountHolderGateway accountHolderGateway;

  @Mock
  private EventGateway eventGateway;

  @Test
  void whenTheEmailAndHexIdAreTheSameInBothBeaconAndLegacyBeacon_thenReturnAMatch() {
    // Setup Beacon
    String hexId = "1D1234123412345";
    String email = "test@test.com";
    UUID accountHolderId = UUID.randomUUID();
    Beacon beacon = new Beacon();
    beacon.setHexId(hexId);
    beacon.setAccountHolderId(accountHolderId);

    // Setup LegacyBeacon
    Map<String, Object> beaconMap = new HashMap<>();
    Map<String, Object> ownerMap = new HashMap<>();
    beaconMap.put("hexId", hexId);
    ownerMap.put("email", email);
    List<LegacyBeacon> foundLegacyBeacons = List.of(
      LegacyBeacon.builder().beacon(beaconMap).owner(ownerMap).build()
    );
    given(legacyBeaconGateway.findAllByHexIdAndEmail(hexId, email))
      .willReturn(foundLegacyBeacons);

    // Setup AccountHolder
    AccountHolder accountHolder = AccountHolder
      .builder()
      .email(email)
      .id(accountHolderId)
      .build();
    given(accountHolderService.getById(accountHolderId))
      .willReturn(accountHolder);

    // Act
    List<LegacyBeacon> matchingLegacyBeacons = legacyBeaconService
      .findMatchingLegacyBeacons(beacon)
      .get();

    // Assert
    assertThat(matchingLegacyBeacons.get(0), is(foundLegacyBeacons.get(0)));
  }

  @Test
  void whenTheEmailIsTheSameButTheHexIdIsNot_thenDontMatch() {
    // Setup Beacon
    String hexId = "does not match";
    String email = "test@test.com";
    UUID accountHolderId = UUID.randomUUID();
    Beacon beacon = new Beacon();
    beacon.setHexId(hexId);
    beacon.setAccountHolderId(accountHolderId);

    // Setup LegacyBeacon
    Map<String, Object> beaconMap = new HashMap<>();
    Map<String, Object> ownerMap = new HashMap<>();
    beaconMap.put("hexId", "different hexId");
    ownerMap.put("email", email);
    given(legacyBeaconGateway.findAllByHexIdAndEmail(hexId, email))
      .willReturn(new ArrayList<>());

    // Setup AccountHolder
    AccountHolder accountHolder = AccountHolder
      .builder()
      .email(email)
      .id(accountHolderId)
      .build();
    given(accountHolderService.getById(accountHolderId))
      .willReturn(accountHolder);

    // Act
    List<LegacyBeacon> matchingLegacyBeacons = legacyBeaconService
      .findMatchingLegacyBeacons(beacon)
      .get();

    // Assert
    assertThat(matchingLegacyBeacons.size(), is(0));
  }

  @Test
  void whenTheHexIdIsTheSameButTheEmailIsNot_thenDontMatch() {
    // Setup Beacon
    String hexId = "1D1234123412345";
    String email = "doesnot@match.com";
    UUID accountHolderId = UUID.randomUUID();
    Beacon beacon = new Beacon();
    beacon.setHexId(hexId);
    beacon.setAccountHolderId(accountHolderId);

    // Setup LegacyBeacon
    Map<String, Object> beaconMap = new HashMap<>();
    Map<String, Object> ownerMap = new HashMap<>();
    beaconMap.put("hexId", hexId);
    ownerMap.put("email", "isnotamatchingemail@address.com");
    given(legacyBeaconGateway.findAllByHexIdAndEmail(hexId, email))
      .willReturn(new ArrayList<>());

    // Setup AccountHolder
    AccountHolder accountHolder = AccountHolder
      .builder()
      .email(email)
      .id(accountHolderId)
      .build();
    given(accountHolderService.getById(accountHolderId))
      .willReturn(accountHolder);

    // Act
    List<LegacyBeacon> matchingLegacyBeacons = legacyBeaconService
      .findMatchingLegacyBeacons(beacon)
      .get();

    // Assert
    assertThat(matchingLegacyBeacons.size(), is(0));
  }

  @Test
  void givenALegacyBeacon_thenItShouldCreateAClaimEvent() throws Exception {
    Map<String, Object> owner = new HashMap<>();
    owner.put("email", "steve@apple.com");

    LegacyBeacon legacyBeacon = LegacyBeacon
      .builder()
      .id(UUID.randomUUID())
      .owner(owner)
      .build();

    legacyBeaconService.claim(legacyBeacon);

    then(eventGateway).should(times(1)).save(isA(LegacyBeaconClaimEvent.class));
  }
}
