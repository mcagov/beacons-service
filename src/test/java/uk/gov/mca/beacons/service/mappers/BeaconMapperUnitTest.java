package uk.gov.mca.beacons.service.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.model.BeaconStatus;

class BeaconMapperUnitTest {

  private BeaconMapper beaconMapper;

  private BeaconDTO beaconDTO;
  private UUID beaconId;

  @BeforeEach
  void init() {
    beaconMapper = new BeaconMapper();
    beaconDTO = new BeaconDTO();
    beaconId = UUID.randomUUID();
    beaconDTO.setId(beaconId);
  }

  @Test
  void shouldSetAllTheFieldsOnTheBeaconFromTheDTO() {
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
    assertThat(
      beacon.getBatteryExpiryDate(),
      is(LocalDateTime.of(2022, 2, 1, 0, 0, 0))
    );
    assertThat(
      beacon.getCreatedDate(),
      is(LocalDateTime.of(2020, 2, 1, 0, 0, 0))
    );
    assertThat(
      beacon.getLastServicedDate(),
      is(LocalDateTime.of(2019, 2, 1, 0, 0, 0))
    );
  }

  @Test
  void shouldCastValuesToNullIfNotDefined() {
    beaconDTO.addAttribute("chkCode", null);

    var beacon = beaconMapper.fromDTO(beaconDTO);

    assertThat(beacon.getHexId(), is(nullValue()));
    assertThat(beacon.getChkCode(), is(nullValue()));
    assertThat(beacon.getBeaconStatus(), is(nullValue()));
    assertThat(beacon.getCreatedDate(), is(nullValue()));
    assertThat(beacon.getBatteryExpiryDate(), is(nullValue()));
    assertThat(beacon.getLastServicedDate(), is(nullValue()));
  }

  @Test
  void shouldThrowAnExceptionIfTheStatusIsNotAValidEnumValue() {
    beaconDTO.addAttribute("status", "RETIRED");
    assertThrows(
      IllegalArgumentException.class,
      () -> {
        beaconMapper.fromDTO(beaconDTO);
      }
    );
  }

  @Test
  void shouldThrowAnExceptionIfItCannotParseAValidDate() {
    var invalidDate = "2020-of-march";
    beaconDTO.addAttribute("createdDate", invalidDate);

    assertThrows(
      DateTimeException.class,
      () -> {
        beaconMapper.fromDTO(beaconDTO);
      }
    );
  }
}
