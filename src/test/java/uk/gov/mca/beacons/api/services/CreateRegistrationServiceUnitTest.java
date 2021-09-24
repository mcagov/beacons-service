package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.dto.CreateEmergencyContactRequest;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;
import uk.gov.mca.beacons.api.gateways.EmergencyContactGateway;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.Person;

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

  @Mock
  private LegacyBeaconService legacyBeaconService;

  @Captor
  private ArgumentCaptor<CreateOwnerRequest> ownerRequestCaptor;

  private UUID beaconId;
  private Beacon beacon;
  private BeaconUse beaconUse;

  @BeforeEach
  void init() {
    beaconId = UUID.randomUUID();
    beacon = new Beacon();
    beacon.setId(beaconId);
    final var owner = new Person();
    beaconUse = new BeaconUse();
    final var emergencyContact = new Person();
    beacon.setOwner(owner);
    beacon.setUses(Collections.singletonList(beaconUse));
    beacon.setEmergencyContacts(Collections.singletonList(emergencyContact));

    given(beaconJpaRepository.save(beacon)).willReturn(beacon);
  }

  @Test
  void shouldSetTheBeaconStatusToNew() {
    createRegistrationService.register(beacon);
    assertThat(beacon.getBeaconStatus(), is(BeaconStatus.NEW));
  }

  @Test
  void shouldSetTheBeaconIdOnTheUse() {
    createRegistrationService.register(beacon);
    assertThat(beaconUse.getBeaconId(), is(beaconId));
  }

  @Test
  void shouldRegisterASingleBeacon() {
    createRegistrationService.register(beacon);

    then(beaconJpaRepository).should(times(1)).save(beacon);
    then(beaconUseJpaRepository).should(times(1)).save(beaconUse);
    then(ownerGateway).should(times(1)).save(isA(CreateOwnerRequest.class));
    then(emergencyContactGateway)
      .should(times(1))
      .save(isA(CreateEmergencyContactRequest.class));
  }

  @Nested
  class WhenTheNewBeaconMatchesALegacyBeaconOnEmailAndHexId_ShouldClaimLegacyBeacon {

    @Test
    void shouldClaimTheLegacyBeacon() {
      createRegistrationService.register(beacon);
      then(legacyBeaconService).should(times(1)).claim(isA(LegacyBeacon.class));
    }
  }
}
