package uk.gov.mca.beacons.service.beacons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import uk.gov.mca.beacons.service.model.Activity;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconStatus;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.Environment;
import uk.gov.mca.beacons.service.model.Purpose;
import uk.gov.mca.beacons.service.model.Registration;
import uk.gov.mca.beacons.service.registrations.RegistrationsService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BeaconsControllerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private RegistrationsService registrationsService;

  private UUID uuid;
  private UUID useUuid;
  private UUID ownerUuid;
  private UUID firstEmergencyContactUuid;
  private UUID secondEmergencyContactUuid;

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
    final var owner = new BeaconPerson();
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
    final var firstEmergencyContact = new BeaconPerson();
    firstEmergencyContact.setFullName("Lady Hamilton");
    firstEmergencyContact.setTelephoneNumber("02392 856621");
    firstEmergencyContact.setAlternativeTelephoneNumber("02392 856622");
    final var secondEmergencyContact = new BeaconPerson();
    secondEmergencyContact.setFullName("Neil Hamilton");
    secondEmergencyContact.setTelephoneNumber("04392 856626");
    secondEmergencyContact.setAlternativeTelephoneNumber("04392 856625");
    beacon.setEmergencyContacts(
      List.of(firstEmergencyContact, secondEmergencyContact)
    );

    final var registration = new Registration();
    registration.setBeacons(List.of(beacon));

    registrationsService.register(registration);
    uuid = beacon.getId();
    useUuid = beacon.getUses().get(0).getId();
  }

  @Test
  void requestAllBeaconControllerShouldReturnSomeBeacons() {
    var request = makeGetRequest("/beacons");

    request.jsonPath("$.meta.pageSize").exists();
    request.jsonPath("$.meta.count").exists();
    request.jsonPath("$.data").exists();
    request.jsonPath("$.data[0].type").isEqualTo("beacon");
    request.jsonPath("$.data[0].id").exists();
    request.jsonPath("$.data[0].attributes.hexId").exists();
    request.jsonPath("$.data[0].attributes.manufacturer").exists();
    request.jsonPath("$.data[0].attributes.status").exists();
    request.jsonPath("$.data[0].attributes.uses[0].environment").exists();
    request.jsonPath("$.data[0].attributes.owner.fullName").exists();
    request
      .jsonPath("$.data[0].attributes.emergencyContacts[0].fullName")
      .exists();
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
}
