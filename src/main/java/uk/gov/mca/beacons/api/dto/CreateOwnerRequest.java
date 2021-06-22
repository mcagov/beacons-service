package uk.gov.mca.beacons.api.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOwnerRequest {

    private UUID beaconId;

    private String fullName;

    private String telephoneNumber;

    private String alternativeTelephoneNumber;

    private String email;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String addressLine4;

    private String townOrCity;

    private String postcode;

    private String county;
}
