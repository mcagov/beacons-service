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
  void shouldDeserialiseTheBeaconUses() throws Exception {
    final String json = "{\"uses\": [{\"environment\":\"MARITIME\"}]}";
    final Beacon beacon = objectMapper.readValue(json, Beacon.class);

    final BeaconUse beaconUse = beacon.getUses().get(0);
    assertNotNull(beaconUse);
    assertThat(beaconUse.getEnvironment(), is(Environment.MARITIME));
  }

  @Test
  void shouldNotSerialiseTheBeaconUse() throws Exception {
    final Beacon beacon = new Beacon();
    final BeaconUse beaconUse = new BeaconUse();
    beacon.setUses(Collections.singletonList(beaconUse));

    final String jsonMap = objectMapper.writeValueAsString(beacon);
    assertKeyDoesNotExist(jsonMap, "uses");
  }

  @Test
  void shouldDeserialiseTheBeaconOwner() throws Exception {
    final String fullName = "Captain Zack Sparrow";
    final String json = "{\"owner\":{\"fullName\":\"" + fullName + "\"}}";
    final Beacon beacon = objectMapper.readValue(json, Beacon.class);

    final BeaconPerson owner = beacon.getOwner();
    assertNotNull(owner);
    assertThat(owner.getFullName(), is(fullName));
  }

  @Test
  void shouldNotSerialiseTheBeaconOwner() throws Exception {
    final Beacon beacon = new Beacon();
    final BeaconPerson owner = new BeaconPerson();
    beacon.setOwner(owner);

    final String jsonMap = objectMapper.writeValueAsString(beacon);
    assertKeyDoesNotExist(jsonMap, "owner");
  }

  @Test
  void shouldDeserialiseTheEmergencyContacts() throws Exception {
    final String fullName = "Captain Zack Sparrow";
    final String json =
      "{\"emergencyContacts\":[{\"fullName\":\"" + fullName + "\"}]}";
    final Beacon beacon = objectMapper.readValue(json, Beacon.class);

    final BeaconPerson emergencyContact = beacon.getEmergencyContacts().get(0);
    assertNotNull(emergencyContact);
    assertThat(emergencyContact.getFullName(), is(fullName));
  }

  @Test
  void shouldNotSerialiseTheEmergencyContacts() throws Exception {
    final Beacon beacon = new Beacon();
    final BeaconPerson emergencyContact = new BeaconPerson();
    beacon.setEmergencyContacts(Collections.singletonList(emergencyContact));

    final String jsonMap = objectMapper.writeValueAsString(beacon);
    assertKeyDoesNotExist(jsonMap, "emergencyContacts");
  }

  private void assertKeyDoesNotExist(String valueToCheck, String key) {
    assertThat(valueToCheck, not(containsString(key)));
  }
}
