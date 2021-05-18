package uk.gov.mca.beacons.service.uses;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@SpringBootTest
class BeaconUsesPatchServiceIntegrationTest {

  @Autowired
  private BeaconUsesPatchService beaconUsesPatchService;

  @MockBean
  private BeaconUseRepository beaconUseRepository;

  @Test
  void shouldThrowAnExceptionIfTheBeaconUseIsNotFound() {
    var beaconId = UUID.randomUUID();
    given(beaconUseRepository.findById(beaconId)).willReturn(Optional.empty());

    assertThrows(
      ResourceNotFoundException.class,
      () -> {
        beaconUsesPatchService.update(beaconId, new BeaconUse());
      }
    );
  }
}
