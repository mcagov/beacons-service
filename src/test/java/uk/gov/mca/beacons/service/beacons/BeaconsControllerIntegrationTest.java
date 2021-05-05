package uk.gov.mca.beacons.service.beacons;

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
    beacon.setBatteryExpiryDate(LocalDateTime.of(2020, 2, 1, 0, 0));
    beacon.setLastServicedDate(LocalDateTime.of(2020, 2, 1, 0, 0));
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
    ownerUuid = beacon.getOwner().getId();
    firstEmergencyContactUuid = beacon.getEmergencyContacts().get(0).getId();
    secondEmergencyContactUuid = beacon.getEmergencyContacts().get(1).getId();
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
    request.jsonPath("$.data[0].type").isEqualTo("beacon");
    request.jsonPath("$.data[0].id").isEqualTo(uuidAsString);
    request.jsonPath("$.data[0].attributes.hexId").exists();
    request.jsonPath("$.data[0].attributes.status").exists();
    request.jsonPath("$.data[0].attributes.manufacturer").exists();
    request.jsonPath("$.data[0].attributes.createdDate").exists();
    request.jsonPath("$.data[0].attributes.model").exists();
    request.jsonPath("$.data[0].attributes.manufacturerSerialNumber").exists();
    request.jsonPath("$.data[0].attributes.chkCode").exists();
    request.jsonPath("$.data[0].attributes.batteryExpiryDate").exists();
    request.jsonPath("$.data[0].attributes.lastServicedDate").exists();
    request
      .jsonPath("$.data[0].relationships.uses.data[0].id")
      .isEqualTo(useUuid.toString());
    request
      .jsonPath("$.data[0].relationships.owner.data[0].id")
      .isEqualTo(ownerUuid.toString());
    request
      .jsonPath("$.data[0].relationships.emergencyContacts.data[0].id")
      .isEqualTo(firstEmergencyContactUuid.toString());
    request
      .jsonPath("$.data[0].relationships.emergencyContacts.data[1].id")
      .isEqualTo(secondEmergencyContactUuid.toString());
    request.jsonPath("$.included").exists();
    request.jsonPath("$.included[0].type").exists();
    request.jsonPath("$.included[0].id").isEqualTo(useUuid.toString());
    request.jsonPath("$.included[1].id").isEqualTo(ownerUuid.toString());
    request
      .jsonPath("$.included[2].id")
      .isEqualTo(firstEmergencyContactUuid.toString());
    request
      .jsonPath("$.included[3].id")
      .isEqualTo(secondEmergencyContactUuid.toString());
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
