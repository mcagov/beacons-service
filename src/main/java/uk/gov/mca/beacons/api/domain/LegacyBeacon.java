package uk.gov.mca.beacons.api.domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.*;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconClaimEvent;
import uk.gov.mca.beacons.api.domain.events.LegacyBeaconEvent;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegacyBeacon {

  private UUID id;
  private BeaconStatus beaconStatus;
  private Map<String, Object> beacon;
  private List<Map<String, Object>> uses;
  private Map<String, Object> owner;
  private List<Map<String, Object>> secondaryOwners;
  private Map<String, Object> emergencyContact;

  @Builder.Default
  private List<LegacyBeaconEvent> history = new ArrayList<>();

  public String getAssociatedEmailAddress() {
    return owner.get("email").toString();
  }

  public void claimFor(AccountHolder accountHolder) throws Exception {
    if (!this.getAssociatedEmailAddress().equals(accountHolder.getEmail())) {
      throw new Exception(
        "A LegacyBeacon can only be claimed by an AccountHolder with a matching email address."
      );
    }

    this.history.add(
        LegacyBeaconClaimEvent
          .builder()
          .id(UUID.randomUUID())
          .whenHappened(OffsetDateTime.now())
          .legacyBeacon(this)
          .accountHolderId(accountHolder.getId())
          .build()
      );
  }

  public boolean hasBeenClaimed() {
    return history
      .stream()
      .anyMatch(event -> event instanceof LegacyBeaconClaimEvent);
  }

  public String getClaimedStatus() {
    if (hasBeenClaimed()) {
      return "CLAIMED";
    }

    return null;
  }
}
