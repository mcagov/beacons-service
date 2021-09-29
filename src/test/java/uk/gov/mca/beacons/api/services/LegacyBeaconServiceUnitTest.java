package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
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
    List<LegacyBeacon> foundLegacyBeacons = List.of(
      LegacyBeacon
        .builder()
        .beacon(Map.of("hexId", hexId))
        .owner(Map.of("email", emailAssociatedWithLegacyBeacon))
        .build()
    );
    given(
      legacyBeaconGateway.findAllByHexIdAndEmail(
        hexId,
        emailAssociatedWithLegacyBeacon
      )
    )
      .willReturn(foundLegacyBeacons);

    List<LegacyBeacon> matchingLegacyBeacons = legacyBeaconService
      .findMatchingLegacyBeacons(beacon)
      .orElseThrow();

    assertThat(matchingLegacyBeacons.get(0), is(foundLegacyBeacons.get(0)));
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
      .willReturn(new ArrayList<>());

    List<LegacyBeacon> matchingLegacyBeacons = legacyBeaconService
      .findMatchingLegacyBeacons(beacon)
      .orElseThrow();

    assertThat(matchingLegacyBeacons.size(), is(0));
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
      .willReturn(new ArrayList<>());

    List<LegacyBeacon> matchingLegacyBeacons = legacyBeaconService
      .findMatchingLegacyBeacons(beacon)
      .orElseThrow();

    assertThat(matchingLegacyBeacons.size(), is(0));
  }
}
