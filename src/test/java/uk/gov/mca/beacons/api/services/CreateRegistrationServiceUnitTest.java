package uk.gov.mca.beacons.api.services;

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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;
import uk.gov.mca.beacons.api.gateways.EmergencyContactGateway;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.jpa.entities.Registration;

@ExtendWith(MockitoExtension.class)
class CreateRegistrationServiceUnitTest {

  @InjectMocks
  private CreateRegistrationService createRegistrationService;

  @Mock
  private BeaconJpaRepository beaconJpaRepository;

  @Mock
  private BeaconUseJpaRepository beaconUseJpaRepository;

  @Mock
  private OwnerGateway ownerGateway;

  @Mock
  private EmergencyContactGateway emergencyContactGateway;

  @Captor
  private ArgumentCaptor<CreateOwnerRequest> ownerRequestCaptor;

  private Registration registration;
  private UUID beaconId;
  private Beacon beacon;
  private BeaconUse beaconUse;
  private Person emergencyContact;

  @BeforeEach
  void init() {
    beaconId = UUID.randomUUID();
    beacon = new Beacon();
    beacon.setId(beaconId);
    final Person owner = new Person();
    beaconUse = new BeaconUse();
    emergencyContact = new Person();
    beacon.setOwner(owner);
    beacon.setUses(Collections.singletonList(beaconUse));
    beacon.setEmergencyContacts(Collections.singletonList(emergencyContact));

    registration = new Registration();
    registration.setBeacons(Collections.singletonList(beacon));

    given(beaconJpaRepository.save(beacon)).willReturn(beacon);
  }

  @Test
  void shouldSetTheBeaconStatusToNew() {
    createRegistrationService.register(registration);
    assertThat(beacon.getBeaconStatus(), is(BeaconStatus.NEW));
  }

  @Test
  void shouldSetTheBeaconIdOnTheUse() {
    createRegistrationService.register(registration);
    assertThat(beaconUse.getBeaconId(), is(beaconId));
  }

  @Test
  void shouldRegisterASingleBeacon() {
    createRegistrationService.register(registration);

    then(beaconJpaRepository).should(times(1)).save(beacon);
    then(beaconUseJpaRepository).should(times(1)).save(beaconUse);
    then(ownerGateway).should(times(1)).create(isA(CreateOwnerRequest.class));
    then(emergencyContactGateway)
      .should(times(1))
      .create(isA(CreateEmergencyContactRequest.class));
  }

  @Test
  void shouldRegisterAMultipleBeaconsUsesAndEmergencyContacts() {
    setupMultipleBeacons();
    createRegistrationService.register(registration);

    then(beaconJpaRepository).should(times(2)).save(beacon);
    then(beaconUseJpaRepository).should(times(4)).save(beaconUse);
    then(emergencyContactGateway)
      .should(times(4))
      .create(isA(CreateEmergencyContactRequest.class));
    then(ownerGateway).should(times(2)).create(isA(CreateOwnerRequest.class));
  }

  private void setupMultipleBeacons() {
    beacon.setUses(Arrays.asList(beaconUse, beaconUse));
    beacon.setEmergencyContacts(
      Arrays.asList(emergencyContact, emergencyContact)
    );
    registration.setBeacons(Arrays.asList(beacon, beacon));
  }
}
