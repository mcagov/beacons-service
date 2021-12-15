package uk.gov.mca.beacons.api.emergencycontact.rest;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContactRegistrationDTO {

  private String fullName;
  private String telephoneNumber;
  private String alternativeTelephoneNumber;
}
