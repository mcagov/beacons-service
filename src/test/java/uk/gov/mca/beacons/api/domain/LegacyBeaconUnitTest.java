package uk.gov.mca.beacons.api.domain;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LegacyBeaconUnitTest {

  @Test
  public void claimFor_throwsIfAccountHolderEmailDoesNotMatchMigratedEmailAddress() {
    LegacyBeacon legacyBeacon = LegacyBeacon
      .builder()
      .owner(Map.of("email", "owner1@beacons.com"))
      .build();

    AccountHolder accountHolder = AccountHolder
      .builder()
      .email("owner2@beacons.co.uk")
      .build();

    Assertions.assertThrows(
      Exception.class,
      () -> legacyBeacon.claimFor(accountHolder)
    );
  }

  @Test
  public void hasBeenClaimed_returnsTrueIfThereIsAClaimEvent()
    throws Exception {
    LegacyBeacon legacyBeacon = LegacyBeacon
      .builder()
      .owner(Map.of("email", "matching_owner@beacons.com"))
      .build();

    AccountHolder accountHolder = AccountHolder
      .builder()
      .email("matching_owner@beacons.com")
      .build();

    legacyBeacon.claimFor(accountHolder);

    Assertions.assertTrue(legacyBeacon.hasBeenClaimed());
  }

  @Test
  public void givenTheLegacyBeaconHasNotBeenClaimed_returnsFalse() {
    LegacyBeacon legacyBeacon = LegacyBeacon
      .builder()
      .owner(Map.of("email", "matching_owner@beacons.com"))
      .build();

    Assertions.assertFalse(legacyBeacon.hasBeenClaimed());
  }
}
