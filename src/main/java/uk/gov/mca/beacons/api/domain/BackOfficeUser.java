package uk.gov.mca.beacons.api.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uk.gov.mca.beacons.api.shared.domain.user.User;

@Getter
@Setter
@Builder
public class BackOfficeUser implements User {

  private UUID id;
  private String fullName;
  private String email;

  @Override
  public UUID getUserId() {
    return getId();
  }
}
