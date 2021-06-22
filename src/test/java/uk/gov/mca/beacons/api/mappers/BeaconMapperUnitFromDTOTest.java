package uk.gov.mca.beacons.api.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.db.Beacon;
import uk.gov.mca.beacons.api.dto.BeaconDTO;
import uk.gov.mca.beacons.api.entities.BeaconStatus;
import uk.gov.mca.beacons.api.hateoas.BeaconLinkStrategy;
import uk.gov.mca.beacons.api.hateoas.HateoasLinkManager;

@ExtendWith(MockitoExtension.class)
class BeaconMapperUnitFromDTOTest {

    private BeaconMapper beaconMapper;

    private BeaconDTO beaconDTO;
    private UUID beaconId;

    @Mock
    private HateoasLinkManager<Beacon> linkManager;

    @Mock
    private BeaconLinkStrategy linkStrategy;

    private Map<String, Object> attributes;

    @BeforeEach
    void init() {
        beaconMapper = new BeaconMapper(linkManager, linkStrategy);
        beaconDTO = new BeaconDTO();
        beaconId = UUID.randomUUID();
        beaconDTO.setId(beaconId);
        attributes = new HashMap<>();
    }

    @Test
    void shouldSetAllTheFieldsOnTheBeaconFromTheDTO() {
        attributes.put("hexId", "1");
        attributes.put("manufacturer", "Trousers");
        attributes.put("model", "ASOS");
        attributes.put("chkCode", "2");
        attributes.put("manufacturerSerialNumber", "3");
        attributes.put("status", "NEW");
        attributes.put("createdDate", "2020-02-01T00:00");
        attributes.put("batteryExpiryDate", "2022-02-01T00:00");
        attributes.put("lastServicedDate", "2019-02-01T00:00");
        beaconDTO.setAttributes(attributes);

        var beacon = beaconMapper.fromDTO(beaconDTO);

        assertThat(beacon.getId(), is(beaconId));
        assertThat(beacon.getHexId(), is("1"));
        assertThat(beacon.getManufacturer(), is("Trousers"));
        assertThat(beacon.getModel(), is("ASOS"));
        assertThat(beacon.getChkCode(), is("2"));
        assertThat(beacon.getManufacturerSerialNumber(), is("3"));
        assertThat(beacon.getBeaconStatus(), is(BeaconStatus.NEW));
        assertThat(
                beacon.getCreatedDate(),
                is(LocalDateTime.of(2020, 2, 1, 0, 0, 0))
        );
        assertThat(beacon.getBatteryExpiryDate(), is(LocalDate.of(2022, 2, 1)));
        assertThat(beacon.getLastServicedDate(), is(LocalDate.of(2019, 2, 1)));
    }

    @Test
    void shouldCastValuesToNullIfNotDefined() {
        attributes.put("chkCode", null);
        beaconDTO.setAttributes(attributes);

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
        attributes.put("status", "RETIRED");
        beaconDTO.setAttributes(attributes);
        assertThrows(
                IllegalArgumentException.class,
                () -> beaconMapper.fromDTO(beaconDTO)
        );
    }

    @Test
    void shouldThrowAnExceptionIfItCannotParseAValidDate() {
        var invalidDate = "2020-of-march";
        attributes.put("createdDate", invalidDate);
        beaconDTO.setAttributes(attributes);

        assertThrows(
                DateTimeException.class,
                () -> beaconMapper.fromDTO(beaconDTO)
        );
    }

    @Test
    void shouldAccuratelyStripTimeInformationFromDateFields() {
        attributes.put("batteryExpiryDate", "2022-02-01T00:00");
        attributes.put("lastServicedDate", "2019-02-01T00:00");
        beaconDTO.setAttributes(attributes);

        var beacon = beaconMapper.fromDTO(beaconDTO);

        assertThat(beacon.getBatteryExpiryDate(), is(LocalDate.of(2022, 2, 1)));
        assertThat(beacon.getLastServicedDate(), is(LocalDate.of(2019, 2, 1)));
    }
}
