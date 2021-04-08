package uk.gov.mca.beacons.service.registrations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.Registration;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@ExtendWith(MockitoExtension.class)
class RegistrationsServiceUnitTest {

  @InjectMocks
  private RegistrationsService registrationsService;

  @Mock
  private BeaconRepository beaconRepository;

  @Mock
  private BeaconPersonRepository beaconPersonRepository;

  @Mock
  private BeaconUseRepository beaconUseRepository;

  private Registration registration;
  private Beacon beacon;
  private BeaconUse beaconUse;
  private BeaconPerson owner;
  private BeaconPerson emergencyContact;

  @BeforeEach
  void init() {
    beacon = new Beacon();
    beacon.setOwner(owner);
    beacon.setUses(Collections.singletonList(beaconUse));
    beacon.setEmergencyContacts(Collections.singletonList(emergencyContact));

    registration = new Registration();
    registration.setBeacons(Collections.singletonList(beacon));
  }

  @Test
  void shouldReturnTheSameRegistrationObjectProvided() {
    final Registration expected = registrationsService.register(registration);

    assertThat(registration, is(expected));
  }

  @Test
  void shouldRegisterASingleBeacon() {
    given(beaconRepository.save(beacon)).willReturn(beacon);

    registrationsService.register(registration);

    then(beaconRepository).should(times(1)).save(beacon);
  }
}
