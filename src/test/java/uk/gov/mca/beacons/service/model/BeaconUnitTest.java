package uk.gov.mca.beacons.service.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class BeaconUnitTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldDeserializeTheBeaconUses() throws Exception {
    final String json =
      "{\"beaconType\":\"PLB\",\"uses\": [{\"environment\":\"MARITIME\"}]}";
    final Beacon beacon = objectMapper.readValue(json, Beacon.class);

    final BeaconUse beaconUse = beacon.getUses().get(0);
    assertNotNull(beaconUse);
    assertThat(beaconUse.getEnvironment(), is(Environment.MARITIME));
  }

  @Test
  void shouldNotSerializeTheBeaconUse() throws Exception {
    final Beacon beacon = new Beacon();
    final BeaconUse beaconUse = new BeaconUse();
    beacon.setUses(Collections.singletonList(beaconUse));

    final String jsonMap = objectMapper.writeValueAsString(beacon);
    assertThat(jsonMap, not(containsString("uses")));
  }
}
