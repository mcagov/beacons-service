package uk.gov.mca.beacons.api.domain.events;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;

@Getter
@Setter
public class LegacyBeaconClaimEvent extends Event {

  private LegacyBeacon legacyBeacon;
  private AccountHolder accountHolder;

  public LegacyBeaconClaimEvent(
    UUID id,
    OffsetDateTime dateTime,
    LegacyBeacon legacyBeacon,
    AccountHolder accountHolder
  ) {
    super(id, dateTime);
    this.legacyBeacon = legacyBeacon;
    this.accountHolder = accountHolder;
  }
}
