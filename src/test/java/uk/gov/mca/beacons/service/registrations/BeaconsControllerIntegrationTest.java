package uk.gov.mca.beacons.service.registrations;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import uk.gov.mca.beacons.service.model.Activity;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
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

  @BeforeEach
  public final void before() {
    final var beacon = new Beacon();
    beacon.setManufacturer("Ocean Signal");
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
  }

  @Test
  void requestAllBeaconControllerShouldReturnSomeBeacons() {
    var request = makeGetRequest("/beacons/");

    request.jsonPath("$.meta.pageSize").exists();
    request.jsonPath("$.meta.count").exists();
    request.jsonPath("$.data").exists();
    request.jsonPath("$.data[0].type").isEqualTo("beacon");
    request.jsonPath("$.data[0].id").exists();
    request.jsonPath("$.data[0].attributes.hexId").exists();
    request.jsonPath("$.data[0].attributes.manufacturer").exists();
    request.jsonPath("$.data[0].attributes.uses[0].environment").exists();
    request.jsonPath("$.data[0].attributes.owner.fullName").exists();
    request
      .jsonPath("$.data[0].attributes.emergencyContacts[0].fullName")
      .exists();
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
