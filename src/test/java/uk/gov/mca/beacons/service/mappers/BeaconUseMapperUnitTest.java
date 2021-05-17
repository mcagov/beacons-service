package uk.gov.mca.beacons.service.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.model.Activity;
import uk.gov.mca.beacons.service.model.Environment;
import uk.gov.mca.beacons.service.model.Purpose;

class BeaconUseMapperUnitTest {

  private BeaconUseMapper beaconUseMapper;
  private BeaconUseDTO beaconUseDto;

  @BeforeEach
  void init() {
    beaconUseMapper = new BeaconUseMapper();
    beaconUseDto = new BeaconUseDTO();
  }

  @Test
  void itShouldSetNullValuesAsNull() {
    beaconUseDto.addAttribute("callSign", null);
    beaconUseDto.addAttribute("vhfRadio", null);
    beaconUseDto.addAttribute("maxCapacity", null);
    var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
    assertThat(beaconUse.getCallSign(), is(nullValue()));
    assertThat(beaconUse.getVhfRadio(), is(nullValue()));
    assertThat(beaconUse.getMaxCapacity(), is(nullValue()));
  }

  @Test
  void itShouldSetUnSetValuesAsNull() {
    var beaconUse = beaconUseMapper.fromDTO(beaconUseDto);
    assertThat(beaconUse.getCallSign(), is(nullValue()));
    assertThat(beaconUse.getVhfRadio(), is(nullValue()));
    assertThat(beaconUse.getMaxCapacity(), is(nullValue()));
  }

  @Test
  void shouldDeserialiseTheEnumValues() {
    beaconUseDto.addAttribute("environment", Environment.MARITIME);
    beaconUseDto.addAttribute("purpose", Purpose.PLEASURE);
    beaconUseDto.addAttribute("activity", Activity.SAILING);

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
    beaconUseDto.addAttribute("environment", "AT_SEA");

    assertThrows(
      IllegalArgumentException.class,
      () -> {
        beaconUseMapper.fromDTO(beaconUseDto);
      }
    );
  }
}
