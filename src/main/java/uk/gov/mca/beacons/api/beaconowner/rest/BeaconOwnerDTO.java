package uk.gov.mca.beacons.api.beaconowner.rest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import lombok.*;
import uk.gov.mca.beacons.api.dto.DomainDTO;
import uk.gov.mca.beacons.api.shared.rest.person.dto.AddressDTO;

public class BeaconOwnerDTO extends DomainDTO<BeaconOwnerDTO.Attributes> {

  // TODO: This is tech debt, it's being left as beaconPerson to keep the
  // TODO: public contract static, should be changed to beaconOwner soon
  private final String type = "beaconPerson";

  @Override
  public String getType() {
    return type;
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Attributes {

    @NotNull
    private String fullName;

    @NotNull
    private String email;

    private String telephoneNumber;

    private String alternativeTelephoneNumber;

    @JsonUnwrapped
    private AddressDTO addressDTO;

    private OffsetDateTime createdDate;

    private OffsetDateTime lastModifiedDate;
    // TODO: DTO should really contain BeaconId attribute;
  }
}
