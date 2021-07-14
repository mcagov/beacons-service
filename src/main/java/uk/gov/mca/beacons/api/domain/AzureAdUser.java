package uk.gov.mca.beacons.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AzureAdUser implements User {

  private String authId;
  private String fullName;
  private String email;
}
