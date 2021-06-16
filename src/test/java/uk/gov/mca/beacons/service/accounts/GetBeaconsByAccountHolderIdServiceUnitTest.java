package uk.gov.mca.beacons.service.accounts;

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
import uk.gov.mca.beacons.service.gateway.BeaconGateway;
import uk.gov.mca.beacons.service.gateway.OwnerGateway;
import uk.gov.mca.beacons.service.gateway.UseGateway;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;

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

  @Test
  void shouldReturnAnEmptyListIfThereAreNoBeaconsForTheAccountId() {
    final var accountId = UUID.randomUUID();
    given(beaconGateway.findAllByAccountHolderId(accountId))
      .willReturn(Collections.emptyList());

    final var result = getBeaconsByAccountHolderIdService.execute(accountId);
    assertThat(result, is(empty()));
  }

  @Test
  void shouldReturnABeaconWithItsUses() {
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

    assertThat(result.size(), is(1));
    final var beaconResult = result.get(0);
    assertThat(beaconResult, is(beacon));
    assertThat(beaconResult.getUses().size(), is(1));
    assertThat(beaconResult.getUses().get(0), is(beaconUse));
  }

  @Test
  void shouldReturnABeaconWithItsOwner() {
    final var accountId = UUID.randomUUID();
    final var beaconId = UUID.randomUUID();
    final var beacon = new Beacon();
    final var owner = new BeaconPerson();

    given(beaconGateway.findAllByAccountHolderId(accountId))
      .willReturn(Collections.singletonList(beacon));
    given(ownerGateway.findByBeaconId(beaconId)).willReturn(owner);
  }
}
