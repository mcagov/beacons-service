package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@ExtendWith(MockitoExtension.class)
class BeaconGatewayImplUnitTest {

  @InjectMocks
  private BeaconGatewayImpl beaconGateway;

  @Mock
  private BeaconJpaRepository beaconRepository;

  @Captor
  private ArgumentCaptor<Beacon> beaconArgumentCapture;

  @Test
  void shouldUpdateTheBeaconForAGivenId() {
    final var beaconId = UUID.randomUUID();
    final var beacon = new Beacon();
    given(beaconRepository.findById(beaconId)).willReturn(Optional.of(beacon));

    beaconGateway.delete(beaconId);

    verify(beaconRepository).save(beaconArgumentCapture.capture());
    final var result = beaconArgumentCapture.getValue();
    assertThat(result.getBeaconStatus(), is(BeaconStatus.DELETED));
  }

  @Test
  void shouldThrowIfABeaconIsNotFoundForDelete() {
    final var beaconId = UUID.randomUUID();
    given(beaconRepository.findById(beaconId)).willReturn(Optional.empty());

    assertThrows(
      ResourceNotFoundException.class,
      () -> {
        beaconGateway.delete(beaconId);
      }
    );
  }
}
