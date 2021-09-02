package uk.gov.mca.beacons.api.controllers;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import uk.gov.mca.beacons.api.domain.Activity;
import uk.gov.mca.beacons.api.domain.BackOfficeUser;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.Environment;
import uk.gov.mca.beacons.api.domain.Purpose;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.jpa.entities.Registration;
import uk.gov.mca.beacons.api.services.CreateRegistrationService;
import uk.gov.mca.beacons.api.services.GetUserService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconsControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private CreateRegistrationService createRegistrationService;

  @MockBean
  private GetUserService getUserService;

  private UUID uuid;
  private UUID useUuid;

  @BeforeEach
  public final void before() {
    final var beacon = new Beacon();
    beacon.setManufacturer("Ocean Signal");
    beacon.setBeaconStatus(BeaconStatus.NEW);
    beacon.setModel("EPIRB1");
    beacon.setManufacturerSerialNumber("1407312904");
    beacon.setChkCode("9480B");
    beacon.setHexId("HEXID123");
    beacon.setBatteryExpiryDate(LocalDate.of(2020, 2, 1));
    beacon.setLastServicedDate(LocalDate.of(2020, 2, 1));
    final var owner = new Person();
    owner.setAddressLine1("2 The Hard");
    owner.setAddressLine2("");
    owner.setFullName("Vice-Admiral Horatio Nelson, 2st Viscount Nelson");
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
    beaconUse.setMainUse(true);
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

    final var registration = new Registration();
    registration.setBeacons(List.of(beacon));

    createRegistrationService.register(registration);
    uuid = beacon.getId();
    useUuid = beacon.getUses().get(0).getId();
  }

  @Test
  void requestBeaconControllerShouldReturnBeaconByUuid() {
    String uuidAsString = uuid.toString();
    var request = makeGetRequest(String.format("/beacons/%s", uuidAsString));

    request.jsonPath("$.data").exists();
    request.jsonPath("$.data.type").isEqualTo("beacon");
    request.jsonPath("$.data.id").isEqualTo(uuidAsString);
    request.jsonPath("$.data.attributes.hexId").exists();
    request.jsonPath("$.data.attributes.status").exists();
    request.jsonPath("$.data.attributes.manufacturer").exists();
    request.jsonPath("$.data.attributes.createdDate").exists();
    request.jsonPath("$.data.attributes.model").exists();
    request.jsonPath("$.data.attributes.manufacturerSerialNumber").exists();
    request.jsonPath("$.data.attributes.chkCode").exists();
    request.jsonPath("$.data.attributes.batteryExpiryDate").exists();
    request.jsonPath("$.data.attributes.lastServicedDate").exists();
    request.jsonPath("$.data.links").exists();
    request
      .jsonPath("$.data.relationships.uses.data[0].id")
      .isEqualTo(useUuid.toString());
    request.jsonPath("$.data.relationships.owner.data[0].id").isNotEmpty();
    request
      .jsonPath("$.data.relationships.emergencyContacts.data[0].id")
      .isNotEmpty();
    request
      .jsonPath("$.data.relationships.emergencyContacts.data[1].id")
      .isNotEmpty();
    request.jsonPath("$.included").exists();
    request.jsonPath("$.included[0].type").exists();
    request.jsonPath("$.included[0].id").isEqualTo(useUuid.toString());
    request.jsonPath("$.included[0].links").exists();
    request.jsonPath("$.included[1].id").isNotEmpty();
    request.jsonPath("$.included[2].id").isNotEmpty();
    request.jsonPath("$.included[3].id").isNotEmpty();
  }

  private WebTestClient.BodyContentSpec makeGetRequest(String url) {
    return webTestClient
      .get()
      .uri(url)
      .exchange()
      .expectStatus()
      .is2xxSuccessful()
      .expectBody();
  }

  @Test
  void shouldReturnTheNotesForABeaconId() throws Exception {
    final String beaconId = uuid.toString();
    final String firstNoteId = getNoteId(createNote(beaconId));
    final String secondNoteId = getNoteId(createNote(beaconId));
    final String expectedResponse = readFile(
      "src/test/resources/fixtures/getNotesByBeaconIdResponse.json"
    )
      .replace("replace-with-first-test-note-id", firstNoteId)
      .replace("replace-with-second-test-note-id", secondNoteId)
      .replace("replace-with-test-beacon-id", beaconId);

    final var response = webTestClient
      .get()
      .uri("/beacons/" + beaconId + "/notes")
      .exchange()
      .expectBody();

    response.json(expectedResponse);
  }

  private String readFile(String filePath) throws IOException {
    return Files.readString(Paths.get(filePath));
  }

  private WebTestClient.BodyContentSpec createNote(String beaconId)
    throws Exception {
    final String createNoteRequest = readFile(
      "src/test/resources/fixtures/createNoteRequest.json"
    )
      .replace("replace-with-test-beacon-id", beaconId);

    final UUID userId = UUID.fromString("344848b9-8a5d-4818-a57d-1815528d543e");
    final String fullName = "Jean ValJean";
    final String email = "24601@jail.fr";
    final User user = BackOfficeUser
      .builder()
      .id(userId)
      .fullName(fullName)
      .email(email)
      .build();

    given(getUserService.getUser(null)).willReturn(user);

    return webTestClient
      .post()
      .uri("/note")
      .body(BodyInserters.fromValue(createNoteRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectBody();
  }

  private String getNoteId(WebTestClient.BodyContentSpec createNoteResponse)
    throws Exception {
    return new ObjectMapper()
      .readValue(
        createNoteResponse.returnResult().getResponseBody(),
        ObjectNode.class
      )
      .get("data")
      .get("id")
      .textValue();
  }
}
