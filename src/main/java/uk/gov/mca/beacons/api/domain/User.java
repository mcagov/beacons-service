package uk.gov.mca.beacons.api.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {

  private UUID authId;
  private String fullName;
  private String email;
}
