package uk.gov.mca.beacons.service.gateway;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;

@ExtendWith(MockitoExtension.class)
class EmergencyContactGatewayImplUnitTest {

  @InjectMocks
  private EmergencyContactGatewayImpl emergencyContactGateway;

  @Mock
  private BeaconPersonRepository beaconPersonRepository;

  @Captor
  private ArgumentCaptor<BeaconPerson> emergencyContactCaptor;

  @Test
  void shouldCreateAnEmergencyContactFromRequestObject() {
    final UUID beaconId = UUID.randomUUID();
    final String fullName = "Souma Matveev";
    final String telephoneNumber = "07777777777";
    final String alternativeTelephoneNumber = "02088888888";
    final String email = "Souma.Matveev@aol.com";
    final String addressLine1 = "1 Street";
    final String addressLine2 = "Kanto";
    final String addressLine3 = "Johto";
    final String addressLine4 = "Sinnoh";
    final String townOrCity = "Lawndale";
    final String postcode = "A1 2BC";
    final String county = "Devonhevonshire";
    final CreateEmergencyContactRequest createEmergencyContactRequest = CreateEmergencyContactRequest
      .builder()
      .beaconId(beaconId)
      .fullName(fullName)
      .telephoneNumber(telephoneNumber)
      .alternativeTelephoneNumber(alternativeTelephoneNumber)
      .email(email)
      .addressLine1(addressLine1)
      .addressLine2(addressLine2)
      .addressLine3(addressLine3)
      .addressLine4(addressLine4)
      .townOrCity(townOrCity)
      .postcode(postcode)
      .county(county)
      .build();

    emergencyContactGateway.save(createEmergencyContactRequest);

    verify(beaconPersonRepository).save(emergencyContactCaptor.capture());
    final BeaconPerson emergencyContact = emergencyContactCaptor.getValue();

    assertThat(emergencyContact.getBeaconId(), is(beaconId));
    assertThat(emergencyContact.getFullName(), is(fullName));
    assertThat(emergencyContact.getTelephoneNumber(), is(telephoneNumber));
    assertThat(
      emergencyContact.getAlternativeTelephoneNumber(),
      is(alternativeTelephoneNumber)
    );
    assertThat(emergencyContact.getEmail(), is(email));
    assertThat(
      emergencyContact.getPersonType(),
      is(PersonType.EMERGENCY_CONTACT)
    );
    assertThat(emergencyContact.getAddressLine1(), is(addressLine1));
    assertThat(emergencyContact.getAddressLine2(), is(addressLine2));
    assertThat(emergencyContact.getAddressLine3(), is(addressLine3));
    assertThat(emergencyContact.getAddressLine4(), is(addressLine4));
    assertThat(emergencyContact.getTownOrCity(), is(townOrCity));
    assertThat(emergencyContact.getPostcode(), is(postcode));
    assertThat(emergencyContact.getCounty(), is(county));
  }
}
