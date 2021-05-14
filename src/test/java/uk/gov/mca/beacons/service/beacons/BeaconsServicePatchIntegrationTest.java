package uk.gov.mca.beacons.service.beacons;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconStatus;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@SpringBootTest
class BeaconsServicePatchIntegrationTest {

  @Autowired
  private BeaconsService beaconService;

  @MockBean
  BeaconRepository mockBeaconRepo;

  @MockBean
  BeaconPersonRepository mockPersonRepo;

  @MockBean
  BeaconUseRepository mockUseRepo;

  @BeforeEach
  public final void before() {
    //final var payload = "{\"data\": {\"type\": \"beacons\",\"id\": \"1\",\"attributes\": {\"model\": \"New model value\",\"manufacturer\": \"New manufacturer value\"}}";
  }

  @Test
  void updateShouldThrowExceptionIfBeaconNotFound() {
    given(mockBeaconRepo.findById(any())).willReturn(Optional.empty());
    assertThrows(
      ResourceNotFoundException.class,
      () -> beaconService.update(UUID.randomUUID(), null)
    );
  }

  @Test
  void updateShouldTryToSaveUpdatedBeacon() {
    var id = UUID.randomUUID();
    var oldBeacon = new Beacon();
    oldBeacon.setId(id);
    given(mockBeaconRepo.findById(id)).willReturn(Optional.of(oldBeacon));
    given(mockPersonRepo.findAllByBeaconId(id))
      .willReturn(Collections.emptyList());
    given(mockUseRepo.findAllByBeaconId(id))
      .willReturn(Collections.emptyList());

    var updateBeacon = new Beacon();
    updateBeacon.setBatteryExpiryDate(LocalDateTime.of(2001, 01, 02, 03, 04));
    updateBeacon.setBeaconStatus(BeaconStatus.NEW);
    updateBeacon.setChkCode("a-chk-code");
    updateBeacon.setCreatedDate(LocalDateTime.of(2002, 11, 12, 13, 14));
    updateBeacon.setHexId("HEXID");
    updateBeacon.setLastServicedDate(LocalDateTime.of(1983, 03, 13, 03, 04));
    updateBeacon.setManufacturer("Beacons-R-Us");
    updateBeacon.setManufacturerSerialNumber("mambonbr5");
    updateBeacon.setModel("Naomi");
    updateBeacon.setReferenceNumber("a-ref-num");

    beaconService.update(id, updateBeacon);

    var beaconCapture = ArgumentCaptor.forClass(Beacon.class);
    verify(mockBeaconRepo).save(beaconCapture.capture());
    assertThat(
      beaconCapture.getValue().getBatteryExpiryDate(),
      is(LocalDateTime.of(2001, 01, 02, 03, 04))
    );
    assertThat(
      beaconCapture.getValue().getBeaconStatus(),
      is(BeaconStatus.NEW)
    );
    assertThat(beaconCapture.getValue().getChkCode(), is("a-chk-code"));
    assertThat(
      beaconCapture.getValue().getCreatedDate(),
      is(LocalDateTime.of(2002, 11, 12, 13, 14))
    );
    assertThat(beaconCapture.getValue().getHexId(), is("HEXID"));
    assertThat(
      beaconCapture.getValue().getLastServicedDate(),
      is(LocalDateTime.of(1983, 03, 13, 03, 04))
    );
    assertThat(beaconCapture.getValue().getManufacturer(), is("Beacons-R-Us"));
    assertThat(
      beaconCapture.getValue().getManufacturerSerialNumber(),
      is("mambonbr5")
    );
    assertThat(beaconCapture.getValue().getModel(), is("Naomi"));
    assertThat(beaconCapture.getValue().getReferenceNumber(), is("a-ref-num"));
  }

  @Test
  void updateShouldTryToSavePartiallyUpdatedBeacon() {
    var id = UUID.randomUUID();
    var oldBeacon = new Beacon();

    oldBeacon.setId(id);
    oldBeacon.setChkCode("a-chk-code");
    oldBeacon.setCreatedDate(LocalDateTime.of(2002, 11, 12, 13, 14));
    oldBeacon.setManufacturerSerialNumber("mambonbr5");
    oldBeacon.setReferenceNumber("a-ref-num");

    oldBeacon.setLastServicedDate(LocalDateTime.of(1992, 11, 8, 03, 04));
    oldBeacon.setManufacturer("Beacon-4-U");
    oldBeacon.setModel("Naomi");

    given(mockBeaconRepo.findById(id)).willReturn(Optional.of(oldBeacon));
    given(mockPersonRepo.findAllByBeaconId(id))
      .willReturn(Collections.emptyList());
    given(mockUseRepo.findAllByBeaconId(id))
      .willReturn(Collections.emptyList());

    var updateBeacon = new Beacon();
    updateBeacon.setBatteryExpiryDate(LocalDateTime.of(2001, 01, 02, 03, 04));
    updateBeacon.setBeaconStatus(BeaconStatus.NEW);
    updateBeacon.setHexId("HEXID");
    updateBeacon.setLastServicedDate(LocalDateTime.of(1983, 03, 13, 03, 04));
    updateBeacon.setManufacturer("Beacons-R-Us");
    updateBeacon.setModel("ASOS");

    beaconService.update(id, updateBeacon);

    var beaconCapture = ArgumentCaptor.forClass(Beacon.class);
    verify(mockBeaconRepo).save(beaconCapture.capture());
    assertThat(
      beaconCapture.getValue().getBatteryExpiryDate(),
      is(LocalDateTime.of(2001, 01, 02, 03, 04))
    );
    assertThat(
      beaconCapture.getValue().getBeaconStatus(),
      is(BeaconStatus.NEW)
    );
    assertThat(beaconCapture.getValue().getChkCode(), is("a-chk-code"));
    assertThat(
      beaconCapture.getValue().getCreatedDate(),
      is(LocalDateTime.of(2002, 11, 12, 13, 14))
    );
    assertThat(beaconCapture.getValue().getHexId(), is("HEXID"));
    assertThat(
      beaconCapture.getValue().getLastServicedDate(),
      is(LocalDateTime.of(1983, 03, 13, 03, 04))
    );
    assertThat(beaconCapture.getValue().getManufacturer(), is("Beacons-R-Us"));
    assertThat(
      beaconCapture.getValue().getManufacturerSerialNumber(),
      is("mambonbr5")
    );
    assertThat(beaconCapture.getValue().getModel(), is("ASOS"));
    assertThat(beaconCapture.getValue().getReferenceNumber(), is("a-ref-num"));
  }
}
