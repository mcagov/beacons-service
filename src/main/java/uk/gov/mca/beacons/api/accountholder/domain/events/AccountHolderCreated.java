package uk.gov.mca.beacons.api.accountholder.domain.events;

import java.time.Instant;
import lombok.Getter;
import org.springframework.lang.NonNull;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.shared.domain.base.DomainEvent;

public class AccountHolderCreated implements DomainEvent {

  private final Instant occurredOn;

  @Getter
  private final AccountHolderId id;

  public AccountHolderCreated(
    @NonNull Instant occurredOn,
    @NonNull AccountHolderId id
  ) {
    this.occurredOn = occurredOn;
    this.id = id;
  }

  @Override
  @NonNull
  public Instant occurredOn() {
    return occurredOn;
  }
}
