package uk.gov.mca.beacons.api.domain.events;

import java.time.OffsetDateTime;
import lombok.Getter;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

@Getter
public class LegacyBeaconClaimEvent extends Event {

  private final LegacyBeacon legacyBeacon;
  private final AccountHolder accountHolder;

  public LegacyBeaconClaimEvent(
    LegacyBeacon legacyBeacon,
    AccountHolder accountHolder,
    OffsetDateTime dateTime
  ) {
    super(dateTime);
    this.legacyBeacon = legacyBeacon;
    this.accountHolder = accountHolder;
  }
}
