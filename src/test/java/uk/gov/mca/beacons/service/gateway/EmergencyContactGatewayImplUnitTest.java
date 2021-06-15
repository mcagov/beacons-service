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
    final CreateEmergencyContactRequest createEmergencyContactRequest = CreateEmergencyContactRequest
      .builder()
      .fullName("Hello World")
      .postcode("A1 2BC")
      .beaconId(beaconId)
      .build();

    emergencyContactGateway.createEmergencyContact(
      createEmergencyContactRequest
    );

    verify(beaconPersonRepository).save(emergencyContactCaptor.capture());

    final BeaconPerson emergencyContact = emergencyContactCaptor.getValue();

    assertThat(emergencyContact.getFullName(), is("Hello World"));
    assertThat(emergencyContact.getPostcode(), is("A1 2BC"));
    assertThat(
      emergencyContact.getPersonType(),
      is(PersonType.EMERGENCY_CONTACT)
    );
    assertThat(emergencyContact.getBeaconId(), is(beaconId));
  }
}
