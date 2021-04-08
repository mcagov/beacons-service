package uk.gov.mca.beacons.service.registrations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconStatus;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.PersonType;
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
  private UUID beaconId;
  private Beacon beacon;
  private BeaconUse beaconUse;
  private BeaconPerson owner;
  private BeaconPerson emergencyContact;

  @BeforeEach
  void init() {
    beaconId = UUID.randomUUID();
    beacon = new Beacon();
    beacon.setId(beaconId);
    owner = new BeaconPerson();
    beaconUse = new BeaconUse();
    emergencyContact = new BeaconPerson();
    beacon.setOwner(owner);
    beacon.setUses(Collections.singletonList(beaconUse));
    beacon.setEmergencyContacts(Collections.singletonList(emergencyContact));

    registration = new Registration();
    registration.setBeacons(Collections.singletonList(beacon));

    given(beaconRepository.save(beacon)).willReturn(beacon);
  }

  @Test
  void shouldReturnTheSameRegistrationObjectProvided() {
    final Registration expected = registrationsService.register(registration);

    assertThat(registration, is(expected));
  }

  @Test
  void shouldSetTheBeaconStatusToNew() {
    registrationsService.register(registration);
    assertThat(beacon.getBeaconStatus(), is(BeaconStatus.NEW));
  }

  @Test
  void shouldSetTheBeaconIdOnTheUse() {
    registrationsService.register(registration);
    assertThat(beaconUse.getBeaconId(), is(beaconId));
  }

  @Test
  void shouldSetTheBeaconIdAndPersonTypeOnTheOwner() {
    registrationsService.register(registration);
    assertThat(owner.getBeaconId(), is(beaconId));
    assertThat(owner.getPersonType(), is(PersonType.OWNER));
  }

  @Test
  void shouldSetTheBeaconIdAndPersonTypeOnTheEmergencyContact() {
    registrationsService.register(registration);
    assertThat(emergencyContact.getBeaconId(), is(beaconId));
    assertThat(
      emergencyContact.getPersonType(),
      is(PersonType.EMERGENCY_CONTACT)
    );
  }

  @Test
  void shouldRegisterASingleBeacon() {
    registrationsService.register(registration);

    then(beaconRepository).should(times(1)).save(beacon);
    then(beaconUseRepository).should(times(1)).save(beaconUse);
    then(beaconPersonRepository).should(times(2)).save(isA(BeaconPerson.class));
    then(beaconPersonRepository).should(times(1)).save(emergencyContact);
    then(beaconPersonRepository).should(times(1)).save(owner);
  }

  @Test
  void shouldRegisterAMultipleBeaconsUsesAndEmergencyContacts() {
    beacon.setUses(Arrays.asList(beaconUse, beaconUse));
    beacon.setEmergencyContacts(
      Arrays.asList(emergencyContact, emergencyContact)
    );
    registration.setBeacons(Arrays.asList(beacon, beacon));

    given(beaconRepository.save(beacon)).willReturn(beacon);

    registrationsService.register(registration);

    then(beaconRepository).should(times(2)).save(beacon);
    then(beaconUseRepository).should(times(4)).save(beaconUse);
    then(beaconPersonRepository).should(times(6)).save(isA(BeaconPerson.class));
  }
}
