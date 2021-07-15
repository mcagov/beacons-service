package uk.gov.mca.beacons.api.controllers;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
class NoteControllerIntegrationTest {

  private Beacon beacon;
  private Beacon createdBeacon;
  private Registration createdRegistration;

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private CreateRegistrationService createRegistrationService;

  @MockBean
  private GetUserService getUserService;

  @BeforeEach
  void init() {
    BeaconUse beaconUse = new BeaconUse();
    beaconUse.setEnvironment(Environment.LAND);
    beaconUse.setPurpose(Purpose.PLEASURE);
    beaconUse.setActivity(Activity.CLIMBING_MOUNTAINEERING);
    beaconUse.setMainUse(true);
    beaconUse.setMoreDetails("I stole a loaf of bread");

    Person emergencyContact = new Person();
    Person owner = new Person();

    String hexId = UUID.randomUUID().toString();
    String manufacturer = "French";
    String model = "Revolution";
    String manufacturerSerialNumber = "2460124601";
    beacon = new Beacon();
    beacon.setHexId(hexId);
    beacon.setManufacturer(manufacturer);
    beacon.setModel(model);
    beacon.setManufacturerSerialNumber(manufacturerSerialNumber);
    beacon.setUses(List.of(beaconUse));
    beacon.setOwner(owner);
    beacon.setEmergencyContacts(List.of(emergencyContact));

    Registration registration = new Registration();
    registration.setBeacons(List.of(beacon));
    createdRegistration = createRegistrationService.register(registration);
    createdBeacon = createdRegistration.getBeacons().get(0);
  }

  @Test
  void shouldCreateAndReturnTheCreatedNote() throws Exception {
    final String createNoteRequest = readFile(
      "src/test/resources/fixtures/createNoteRequest.json"
    )
      .replace("replace-with-test-beacon-id", createdBeacon.getId().toString());

    final String createNoteResponse = readFile(
      "src/test/resources/fixtures/createNoteResponse.json"
    )
      .replace("replace-with-test-beacon-id", createdBeacon.getId().toString());

    final UUID personId = UUID.fromString(
      "344848b9-8a5d-4818-a57d-1815528d543e"
    );
    final String fullName = "Jean ValJean";
    final String email = "24601@jail.fr";
    final User user = User
      .builder()
      .authId(personId)
      .fullName(fullName)
      .email(email)
      .build();

    given(getUserService.getUser()).willReturn(user);

    var response = webTestClient
      .post()
      .uri("/note")
      .body(BodyInserters.fromValue(createNoteRequest))
      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
      .exchange()
      .expectBody();

    response.json(createNoteResponse);
  }

  private String readFile(String filePath) throws IOException {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }
}
