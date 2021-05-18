package uk.gov.mca.beacons.service.uses;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.model.Activity;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.Environment;
import uk.gov.mca.beacons.service.model.Purpose;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@SpringBootTest
class BeaconUsesPatchServiceIntegrationTest {

  @Autowired
  private BeaconUsesPatchService beaconUsesPatchService;

  @MockBean
  private BeaconUseRepository beaconUseRepository;

  @Test
  void shouldThrowAnExceptionIfTheBeaconUseIsNotFound() {
    var id = UUID.randomUUID();
    given(beaconUseRepository.findById(id)).willReturn(Optional.empty());

    assertThrows(
      ResourceNotFoundException.class,
      () -> {
        beaconUsesPatchService.update(id, new BeaconUse());
      }
    );
  }

  @Test
  void shouldUpdateAllTheFieldsOnTheBeaconUse() {
    var id = UUID.randomUUID();
    var existingBeacon = new BeaconUse();

    existingBeacon.setEnvironment(Environment.MARITIME);
    existingBeacon.setPurpose(Purpose.PLEASURE);
    existingBeacon.setActivity(Activity.SAILING);
    existingBeacon.setCallSign("call him");
    existingBeacon.setVhfRadio(true);
    existingBeacon.setFixedVhfRadio(true);
    existingBeacon.setFixedVhfRadioValue("+117");
    existingBeacon.setPortableVhfRadio(true);
    existingBeacon.setPortableVhfRadioValue("+127");
    existingBeacon.setSatelliteTelephone(true);
    existingBeacon.setSatelliteTelephoneValue("+137");

    given(beaconUseRepository.findById(id))
      .willReturn(Optional.of(existingBeacon));

    var argumentCapture = ArgumentCaptor.forClass(BeaconUse.class);
    then(beaconUseRepository).should(times(1)).save(argumentCapture.capture());
  }

  @Test
  void shouldPartiallyUpdateTheBeaconUse() {
    var argumentCapture = ArgumentCaptor.forClass(BeaconUse.class);
    then(beaconUseRepository).should(times(1)).save(argumentCapture.capture());
  }
}
