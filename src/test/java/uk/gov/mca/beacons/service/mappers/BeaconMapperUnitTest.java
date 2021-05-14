package uk.gov.mca.beacons.service.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.model.BeaconStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class BeaconMapperUnitTest {

    private BeaconMapper beaconMapper;

    @BeforeEach
    void init() {
        beaconMapper = new BeaconMapper();
    }

    @Test
    void shouldSetAllTheFieldsOnTheBeaconFromTheDTO() {
        var beaconId = UUID.randomUUID();
        var beaconDTO = new BeaconDTO();
        beaconDTO.setId(beaconId);
        beaconDTO.addAttribute("hexId", "1");
        beaconDTO.addAttribute("manufacturer", "Trousers");
        beaconDTO.addAttribute("model", "ASOS");
        beaconDTO.addAttribute("chkCode", "2");
        beaconDTO.addAttribute("manufacturerSerialNumber", "3");
        beaconDTO.addAttribute("status", "NEW");
        beaconDTO.addAttribute("createdDate", "2020-02-01T00:00");
        beaconDTO.addAttribute("batteryExpiryDate", "2022-02-01T00:00");
        beaconDTO.addAttribute("lastServicedDate", "2019-02-01T00:00");

        var beacon = beaconMapper.fromDTO(beaconDTO);

        assertThat(beacon.getId(), is(beaconId));
        assertThat(beacon.getHexId(), is("1"));
        assertThat(beacon.getManufacturer(), is("Trousers"));
        assertThat(beacon.getModel(), is("ASOS"));
        assertThat(beacon.getChkCode(), is("2"));
        assertThat(beacon.getManufacturerSerialNumber(), is("3"));
        assertThat(beacon.getBeaconStatus(), is(BeaconStatus.NEW));
        assertThat(beacon.getCreatedDate(), is(LocalDateTime.of(2020, 2, 1,0,0,0)));
        assertThat(beacon.getBatteryExpiryDate(), is(LocalDateTime.of(2022, 2, 1,0,0,0)));
        assertThat(beacon.getLastServicedDate(), is(LocalDateTime.of(2019, 2, 1,0,0,0)));
    }
}
