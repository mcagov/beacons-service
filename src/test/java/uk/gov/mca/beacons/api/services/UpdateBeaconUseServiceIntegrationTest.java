package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;

@SpringBootTest
class UpdateBeaconUseServiceIntegrationTest {

    @Autowired
    private BeaconsService beaconService;

    @MockBean
    private BeaconJpaRepository mockBeaconRepo;

    @MockBean
    private BeaconPersonJpaRepository mockPersonRepo;

    @MockBean
    private BeaconUseJpaRepository mockUseRepo;

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
        updateBeacon.setBatteryExpiryDate(LocalDate.of(2001, 1, 2));
        updateBeacon.setBeaconStatus(BeaconStatus.NEW);
        updateBeacon.setChkCode("a-chk-code");
        updateBeacon.setCreatedDate(LocalDateTime.of(2002, 11, 12, 13, 14));
        updateBeacon.setHexId("HEXID");
        updateBeacon.setLastServicedDate(LocalDate.of(1983, 3, 13));
        updateBeacon.setManufacturer("Beacons-R-Us");
        updateBeacon.setManufacturerSerialNumber("mambonbr5");
        updateBeacon.setModel("Naomi");
        updateBeacon.setReferenceNumber("a-ref-num");

        beaconService.update(id, updateBeacon);

        var beaconCapture = ArgumentCaptor.forClass(Beacon.class);
        verify(mockBeaconRepo).save(beaconCapture.capture());
        assertThat(
                beaconCapture.getValue().getBatteryExpiryDate(),
                is(LocalDate.of(2001, 1, 2))
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
                is(LocalDate.of(1983, 3, 13))
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

        oldBeacon.setLastServicedDate(LocalDate.of(1992, 11, 8));
        oldBeacon.setManufacturer("Beacon-4-U");
        oldBeacon.setModel("Naomi");

        given(mockBeaconRepo.findById(id)).willReturn(Optional.of(oldBeacon));
        given(mockPersonRepo.findAllByBeaconId(id))
                .willReturn(Collections.emptyList());
        given(mockUseRepo.findAllByBeaconId(id))
                .willReturn(Collections.emptyList());

        var updateBeacon = new Beacon();
        updateBeacon.setBatteryExpiryDate(LocalDate.of(2001, 1, 2));
        updateBeacon.setBeaconStatus(BeaconStatus.NEW);
        updateBeacon.setHexId("HEXID");
        updateBeacon.setLastServicedDate(LocalDate.of(1983, 3, 13));
        updateBeacon.setManufacturer("Beacons-R-Us");
        updateBeacon.setModel("ASOS");

        beaconService.update(id, updateBeacon);

        var beaconCapture = ArgumentCaptor.forClass(Beacon.class);
        verify(mockBeaconRepo).save(beaconCapture.capture());
        assertThat(
                beaconCapture.getValue().getBatteryExpiryDate(),
                is(LocalDate.of(2001, 1, 2))
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
                is(LocalDate.of(1983, 3, 13))
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
