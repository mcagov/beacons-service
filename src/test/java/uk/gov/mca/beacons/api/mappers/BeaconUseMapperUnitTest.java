package uk.gov.mca.beacons.api.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import uk.gov.mca.beacons.api.db.BeaconUse;
import uk.gov.mca.beacons.api.dto.BeaconUseDTO;
import uk.gov.mca.beacons.api.entities.Activity;
import uk.gov.mca.beacons.api.entities.Environment;
import uk.gov.mca.beacons.api.entities.Purpose;
import uk.gov.mca.beacons.api.hateoas.BeaconUseLinkStrategy;
import uk.gov.mca.beacons.api.hateoas.HateoasLinkManager;

class BeaconUseMapperUnitTest {

    private BeaconUseMapper beaconUseMapper;
    private BeaconUseDTO beaconUseDto;

    @Mock
    private HateoasLinkManager<BeaconUse> linkManager;

    @Mock
    private BeaconUseLinkStrategy linkStrategy;

    private Map<String, Object> attributes;

    @BeforeEach
    void init() {
        beaconUseMapper = new BeaconUseMapper(linkManager, linkStrategy);
        beaconUseDto = new BeaconUseDTO();
        attributes = new HashMap<>();
    }

    @Test
    void shouldSetTheMainUseToFalseForAStringWithValueFalse() {
        attributes.put("mainUse", "false");
        beaconUseDto.setAttributes(attributes);

        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertFalse(beaconUse.getMainUse());
    }

    @Test
    void shouldSetStringFields() {
        attributes.put("otherActivity", "sailing");
        beaconUseDto.setAttributes(attributes);

        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertThat(beaconUse.getOtherActivity(), is("sailing"));
    }

    @Test
    void shouldSetBooleanValues() {
        attributes.put("mainUse", false);
        attributes.put("vhfRadio", true);
        attributes.put("fixedVhfRadio", "true");
        attributes.put("portableVhfRadio", false);
        attributes.put("satelliteTelephone", "false");
        beaconUseDto.setAttributes(attributes);
        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertFalse(beaconUse.getMainUse());
        assertTrue(beaconUse.getVhfRadio());
        assertTrue(beaconUse.getFixedVhfRadio());
        assertFalse(beaconUse.getPortableVhfRadio());
        assertFalse(beaconUse.getSatelliteTelephone());
    }

    @Test
    void shouldSetBooleanValuesToNull() {
        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertThat(beaconUse.getMainUse(), is(nullValue()));
        assertThat(beaconUse.getVhfRadio(), is(nullValue()));
        assertThat(beaconUse.getFixedVhfRadio(), is(nullValue()));
        assertThat(beaconUse.getPortableVhfRadio(), is(nullValue()));
        assertThat(beaconUse.getSatelliteTelephone(), is(nullValue()));
    }

    @Test
    void shouldSetNumericalValues() {
        attributes.put("maxCapacity", 1);
        beaconUseDto.setAttributes(attributes);
        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertThat(beaconUse.getMaxCapacity(), is(1));
    }

    @Test
    void shouldSetNumericalValuesAsStrings() {
        attributes.put("maxCapacity", "1");
        beaconUseDto.setAttributes(attributes);
        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertThat(beaconUse.getMaxCapacity(), is(1));
    }

    @Test
    void shouldThrowAnExceptionIfAnInvalidNumber() {
        attributes.put("maxCapacity", "NaN");
        beaconUseDto.setAttributes(attributes);
        assertThrows(
                NumberFormatException.class,
                () -> beaconUseMapper.fromDTO(beaconUseDto)
        );
    }

    @Test
    void shouldSetNullValuesForDifferentPrimitiveTypesAsNull() {
        attributes.put("callSign", null);
        attributes.put("vhfRadio", null);
        attributes.put("maxCapacity", null);
        beaconUseDto.setAttributes(attributes);
        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertThat(beaconUse.getCallSign(), is(nullValue()));
        assertThat(beaconUse.getVhfRadio(), is(nullValue()));
        assertThat(beaconUse.getMaxCapacity(), is(nullValue()));
    }

    @Test
    void shouldSetUndefinedValuesAsNull() {
        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
        assertThat(beaconUse.getCallSign(), is(nullValue()));
        assertThat(beaconUse.getVhfRadio(), is(nullValue()));
        assertThat(beaconUse.getMaxCapacity(), is(nullValue()));
    }

    @Test
    void shouldDeserialiseTheEnumValues() {
        attributes.put("environment", Environment.MARITIME);
        attributes.put("purpose", Purpose.PLEASURE);
        attributes.put("activity", Activity.SAILING);
        beaconUseDto.setAttributes(attributes);

        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);

        assertThat(beaconUse.getEnvironment(), is(Environment.MARITIME));
        assertThat(beaconUse.getPurpose(), is(Purpose.PLEASURE));
        assertThat(beaconUse.getActivity(), is(Activity.SAILING));
    }

    @Test
    void shouldHandleNullEnumValues() {
        var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);

        assertThat(beaconUse.getEnvironment(), is(nullValue()));
        assertThat(beaconUse.getPurpose(), is(nullValue()));
        assertThat(beaconUse.getActivity(), is(nullValue()));
    }

    @Test
    void shouldThrowAnExceptionIfTheEnumValuesAreNotValid() {
        attributes.put("environment", "AT_SEA");
        beaconUseDto.setAttributes(attributes);

        assertThrows(
                IllegalArgumentException.class,
                () -> beaconUseMapper.fromDTO(beaconUseDto)
        );
    }
}
