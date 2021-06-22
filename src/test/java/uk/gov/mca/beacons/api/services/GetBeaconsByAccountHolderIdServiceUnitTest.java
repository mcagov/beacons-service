package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.db.Beacon;
import uk.gov.mca.beacons.api.db.BeaconUse;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.gateways.BeaconGateway;
import uk.gov.mca.beacons.api.gateways.EmergencyContactGateway;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.gateways.UseGateway;

@ExtendWith(MockitoExtension.class)
class GetBeaconsByAccountHolderIdServiceUnitTest {

  @InjectMocks
  private GetBeaconsByAccountHolderIdService getBeaconsByAccountHolderIdService;

  @Mock
  private BeaconGateway beaconGateway;

  @Mock
  private UseGateway useGateway;

  @Mock
  private OwnerGateway ownerGateway;

  @Mock
  private EmergencyContactGateway emergencyContactGateway;

  @Test
  void shouldReturnAnEmptyListIfThereAreNoBeaconsFound() {
    final var accountId = UUID.randomUUID();
    given(beaconGateway.findAllByAccountHolderId(accountId))
      .willReturn(Collections.emptyList());

    final var result = getBeaconsByAccountHolderIdService.execute(accountId);
    assertThat(result, is(empty()));
  }

  @Test
  void shouldReturnASingleBeacon() {
    final var accountId = UUID.randomUUID();
    final var beacon = new Beacon();

    given(beaconGateway.findAllByAccountHolderId(accountId))
      .willReturn(Collections.singletonList(beacon));

    final var result = getBeaconsByAccountHolderIdService.execute(accountId);
    assertThat(result.size(), is(1));
    assertThat(result.get(0), is(beacon));
  }

  @Test
  void shouldReturnABeaconsUses() {
    final var accountId = UUID.randomUUID();
    final var beaconId = UUID.randomUUID();
    final var beacon = new Beacon();
    beacon.setId(beaconId);
    final var beaconUse = new BeaconUse();

    given(beaconGateway.findAllByAccountHolderId(accountId))
      .willReturn(Collections.singletonList(beacon));
    given(useGateway.findAllByBeaconId(beaconId))
      .willReturn(Collections.singletonList(beaconUse));

    final var result = getBeaconsByAccountHolderIdService.execute(accountId);
    assertThat(result.get(0).getUses().size(), is(1));
    assertThat(result.get(0).getUses().get(0), is(beaconUse));
  }

  @Test
  void shouldReturnABeaconsOwner() {
    final var accountId = UUID.randomUUID();
    final var beaconId = UUID.randomUUID();
    final var beacon = new Beacon();
    beacon.setId(beaconId);
    final var owner = new Person();

    given(beaconGateway.findAllByAccountHolderId(accountId))
      .willReturn(Collections.singletonList(beacon));
    given(ownerGateway.findByBeaconId(beaconId)).willReturn(owner);

    final var result = getBeaconsByAccountHolderIdService.execute(accountId);
    assertThat(result.get(0).getOwner(), is(owner));
  }

  @Test
  void shouldReturnABeaconsEmergencyContacts() {
    final var accountId = UUID.randomUUID();
    final var beaconId = UUID.randomUUID();
    final var beacon = new Beacon();
    beacon.setId(beaconId);
    final var emergencyContact = new Person();

    given(beaconGateway.findAllByAccountHolderId(accountId))
      .willReturn(Collections.singletonList(beacon));
    given(emergencyContactGateway.findAllByBeaconId(beaconId))
      .willReturn(Collections.singletonList(emergencyContact));

    final var result = getBeaconsByAccountHolderIdService.execute(accountId);
    assertThat(result.get(0).getEmergencyContacts().size(), is(1));
    assertThat(
      result.get(0).getEmergencyContacts().get(0),
      is(emergencyContact)
    );
  }
}
