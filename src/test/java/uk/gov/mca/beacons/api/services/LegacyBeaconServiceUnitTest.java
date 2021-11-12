package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconEvent;
import uk.gov.mca.beacons.api.gateways.AccountHolderGateway;
import uk.gov.mca.beacons.api.gateways.LegacyBeaconGateway;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@ExtendWith(MockitoExtension.class)
public class LegacyBeaconServiceUnitTest {

  @InjectMocks
  private LegacyBeaconService legacyBeaconService;

  @Mock
  private LegacyBeaconGateway legacyBeaconGateway;

  @Mock
  private AccountHolderGateway accountHolderGateway;

  @Test
  void whenTheEmailAndHexIdAreTheSameInBothBeaconAndLegacyBeacon_thenReturnAMatch() {
    UUID accountHolderId = UUID.randomUUID();
    String accountHolderEmail = "test@test.com";
    AccountHolder accountHolder = AccountHolder
      .builder()
      .email(accountHolderEmail)
      .id(accountHolderId)
      .build();
    given(accountHolderGateway.getById(accountHolderId))
      .willReturn(accountHolder);

    String hexId = "1D1234123412345";
    Beacon beacon = new Beacon();
    beacon.setHexId(hexId);
    beacon.setAccountHolderId(accountHolderId);

    String emailAssociatedWithLegacyBeacon = accountHolderEmail;
    Optional<List<LegacyBeacon>> foundLegacyBeacons = Optional.of(
      List.of(
        LegacyBeacon
          .builder()
          .beacon(Map.of("hexId", hexId))
          .owner(Map.of("email", emailAssociatedWithLegacyBeacon))
          .build()
      )
    );
    given(
      legacyBeaconGateway.findAllByHexIdAndEmail(
        hexId,
        emailAssociatedWithLegacyBeacon
      )
    )
      .willReturn(foundLegacyBeacons);

    Optional<List<LegacyBeacon>> matchingLegacyBeacons = legacyBeaconService.findMatchingLegacyBeacons(
      beacon
    );

    assertThat(matchingLegacyBeacons, is(foundLegacyBeacons));
  }

  @Test
  void whenTheEmailIsTheSameButTheHexIdIsNot_thenDontMatch() {
    String accountHolderEmail = "test@test.com";
    UUID accountHolderId = UUID.randomUUID();
    AccountHolder accountHolder = AccountHolder
      .builder()
      .email(accountHolderEmail)
      .id(accountHolderId)
      .build();
    given(accountHolderGateway.getById(accountHolderId))
      .willReturn(accountHolder);

    String hexId = "does not match";
    Beacon beacon = new Beacon();
    beacon.setHexId(hexId);
    beacon.setAccountHolderId(accountHolderId);

    given(legacyBeaconGateway.findAllByHexIdAndEmail(hexId, accountHolderEmail))
      .willReturn(Optional.empty());

    Optional<List<LegacyBeacon>> matchingLegacyBeacons = legacyBeaconService.findMatchingLegacyBeacons(
      beacon
    );

    assertTrue(matchingLegacyBeacons.isEmpty());
  }

  @Test
  void whenTheHexIdIsTheSameButTheEmailIsNot_thenDontMatch() {
    String accountHolderEmail = "doesNotMatch@AnyLegacyBeacons.com";
    UUID accountHolderId = UUID.randomUUID();
    AccountHolder accountHolder = AccountHolder
      .builder()
      .email(accountHolderEmail)
      .id(accountHolderId)
      .build();
    given(accountHolderGateway.getById(accountHolderId))
      .willReturn(accountHolder);

    String hexId = "1D1234123412345";
    Beacon beacon = new Beacon();
    beacon.setHexId(hexId);
    beacon.setAccountHolderId(accountHolderId);

    given(legacyBeaconGateway.findAllByHexIdAndEmail(hexId, accountHolderEmail))
      .willReturn(Optional.empty());

    Optional<List<LegacyBeacon>> matchingLegacyBeacons = legacyBeaconService.findMatchingLegacyBeacons(
      beacon
    );

    assertTrue(matchingLegacyBeacons.isEmpty());
  }

  @Test
  void whenTheLegacyBeaconHasAlreadyBeenClaimed_DoNotClaimAgain()
    throws Exception {
    AccountHolder accountHolder = AccountHolder
      .builder()
      .email("test@test.com")
      .id(UUID.randomUUID())
      .build();

    LegacyBeacon legacyBeacon = LegacyBeacon
      .builder()
      .owner(Map.of("email", "test@test.com"))
      .build();
    legacyBeacon.claimFor(accountHolder);

    given(accountHolderGateway.getByEmail("test@test.com"))
      .willReturn(accountHolder);

    legacyBeaconService.claim(legacyBeacon);

    verify(legacyBeaconGateway, never()).save(legacyBeacon);
  }
}
