package uk.gov.mca.beacons.api.serializer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.api.domain.Activity;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.Environment;
import uk.gov.mca.beacons.api.domain.Purpose;
import uk.gov.mca.beacons.api.dto.BeaconsSearchResultDTO;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.Person;

class BeaconsSearchResultDTOSerializerTest {

  private static final String JSON_RESOURCE_EMPTY =
    "src/test/resources/fixtures/beaconSearchEmpty.json";
  private static final String JSON_RESOURCE =
    "src/test/resources/fixtures/beaconSearch.json";

  BeaconsSearchResultDTO beaconsSearchResultDTO;
  JsonGenerator jsonGenerator;
  SerializerProvider serializerProvider;
  Writer jsonWriter;

  @BeforeEach
  void init() throws IOException {
    beaconsSearchResultDTO = new BeaconsSearchResultDTO();

    beaconsSearchResultDTO.setBeacons(List.of());
    jsonWriter = new StringWriter();
    jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    serializerProvider = new ObjectMapper().getSerializerProvider();
  }

  @Test
  void shouldSerializeResultForZeroResultsAsExpected() throws IOException {
    final String json = Files
      .readString(Paths.get(JSON_RESOURCE_EMPTY))
      .replaceAll("[\\n\\t ]", "");
    final var serializer = new BeaconsSearchResultSerializer();
    beaconsSearchResultDTO.setBeacons(List.of());

    serializer.serialize(
      beaconsSearchResultDTO,
      jsonGenerator,
      serializerProvider
    );
    jsonGenerator.flush();

    assertThat(
      jsonWriter.toString().replaceAll("[\\n\\t ]", ""),
      is(equalTo(json))
    );
  }

  @Test
  void shouldSerializeResultForOneResultAsExpected() throws IOException {
    final var beacon = new Beacon();
    beacon.setId(UUID.fromString("97b306aa-cbd0-4f09-aa24-2d876b983efb"));
    beacon.setBeaconStatus(BeaconStatus.NEW);
    beacon.setHexId("Hex me");
    beacon.setManufacturer("Ocean Signal");
    beacon.setCreatedDate(LocalDateTime.of(2020, 2, 1, 0, 0));
    beacon.setModel("EPIRB1");
    beacon.setManufacturerSerialNumber("1407312904");
    beacon.setChkCode("9480B");
    beacon.setBatteryExpiryDate(LocalDate.of(2020, 2, 1));
    beacon.setLastServicedDate(LocalDate.of(2020, 2, 1));
    final var owner = new Person();
    owner.setAddressLine1("1 The Hard");
    owner.setAddressLine2("");
    owner.setFullName("Vice-Admiral Horatio Nelson, 1st Viscount Nelson");
    owner.setEmail("nelson@royalnavy.mod.uk");
    owner.setTelephoneNumber("02392 856624");
    owner.setTownOrCity("Portsmouth");
    owner.setCounty("Hampshire");
    owner.setPostcode("PO1 3DT");
    beacon.setOwner(owner);
    final var beaconUse = new BeaconUse();
    beaconUse.setEnvironment(Environment.MARITIME);
    beaconUse.setPurpose(Purpose.COMMERCIAL);
    beaconUse.setActivity(Activity.SAILING);
    beaconUse.setVesselName("HMS Victory");
    beaconUse.setMaxCapacity(180);
    beaconUse.setAreaOfOperation("Cape of Trafalgar");
    beaconUse.setMoreDetails("More details of this vessel");
    beacon.setUses(List.of(beaconUse));
    final var firstEmergencyContact = new Person();
    firstEmergencyContact.setFullName("Lady Hamilton");
    firstEmergencyContact.setTelephoneNumber("02392 856621");
    firstEmergencyContact.setAlternativeTelephoneNumber("02392 856622");
    final var secondEmergencyContact = new Person();
    secondEmergencyContact.setFullName("Neil Hamilton");
    secondEmergencyContact.setTelephoneNumber("04392 856626");
    secondEmergencyContact.setAlternativeTelephoneNumber("04392 856625");
    beacon.setEmergencyContacts(
      List.of(firstEmergencyContact, secondEmergencyContact)
    );
    beaconsSearchResultDTO.setBeacons(List.of(beacon));

    final var serializer = new BeaconsSearchResultSerializer();
    serializer.serialize(
      beaconsSearchResultDTO,
      jsonGenerator,
      serializerProvider
    );
    jsonGenerator.flush();

    final String expectedJson = Files
      .readString(Paths.get(JSON_RESOURCE))
      .replaceAll("[\\n\\t ]", "");
    assertThat(
      jsonWriter.toString().replaceAll("[\\n\\t ]", ""),
      is(equalTo(expectedJson))
    );
  }
}
