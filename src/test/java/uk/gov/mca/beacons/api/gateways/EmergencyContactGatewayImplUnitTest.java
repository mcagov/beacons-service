package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Person;

@ExtendWith(MockitoExtension.class)
class EmergencyContactGatewayImplUnitTest {

  @InjectMocks
  private EmergencyContactGatewayImpl emergencyContactGateway;

  @Mock
  private BeaconPersonJpaRepository beaconPersonRepository;

  @Captor
  private ArgumentCaptor<Person> emergencyContactCaptor;

  @Test
  void shouldCreateAnEmergencyContactFromRequestObject() {
    final UUID beaconId = UUID.randomUUID();
    final UUID accountHolderId = UUID.randomUUID();
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
      .accountHolderId(accountHolderId)
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
    final Person emergencyContact = emergencyContactCaptor.getValue();

    assertThat(emergencyContact.getBeaconId(), is(beaconId));
    assertThat(emergencyContact.getAccountHolderId(), is(accountHolderId));
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

  @Test
  void shouldOverrideTheCreatedDateToNow() {
    final var dateInThePast = LocalDateTime.now();
    final var createEmergencyContactRequest = CreateEmergencyContactRequest
      .builder()
      .createdDate(dateInThePast)
      .build();

    emergencyContactGateway.save(createEmergencyContactRequest);

    verify(beaconPersonRepository).save(emergencyContactCaptor.capture());
    final Person emergencyContact = emergencyContactCaptor.getValue();

    assertThat(emergencyContact.getCreatedDate(), is(not(dateInThePast)));
  }

  @Test
  void shouldOverrideTheLastModifiedDateToNow() {
    final var dateInThePast = LocalDateTime.now();
    final var createEmergencyContactRequest = CreateEmergencyContactRequest
      .builder()
      .lastModifiedDate(dateInThePast)
      .build();

    emergencyContactGateway.save(createEmergencyContactRequest);

    verify(beaconPersonRepository).save(emergencyContactCaptor.capture());
    final Person emergencyContact = emergencyContactCaptor.getValue();

    assertThat(emergencyContact.getLastModifiedDate(), is(not(dateInThePast)));
  }
}
