// package uk.gov.mca.beacons.service.beacons;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.UUID;

// import com.jayway.jsonpath.JsonPath;

// import org.hibernate.type.WrapperBinaryType;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.reactive.server.WebTestClient;
// import org.springframework.web.reactive.function.BodyInserters;
// import org.springframework.web.reactive.function.client.WebClient;

// import uk.gov.mca.beacons.service.dto.WrapperDTO;
// import uk.gov.mca.beacons.service.dto.BeaconDTO;
// import uk.gov.mca.beacons.service.model.Activity;
// import uk.gov.mca.beacons.service.model.Beacon;
// import uk.gov.mca.beacons.service.model.BeaconPerson;
// import uk.gov.mca.beacons.service.model.BeaconStatus;
// import uk.gov.mca.beacons.service.model.BeaconUse;
// import uk.gov.mca.beacons.service.model.Environment;
// import uk.gov.mca.beacons.service.model.Purpose;
// import uk.gov.mca.beacons.service.model.Registration;
// import uk.gov.mca.beacons.service.registrations.RegistrationsService;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureWebTestClient
// class BeaconsControllerPatchIntegrationTest {

//     @Autowired
//     private WebTestClient webTestClient;

//     // @Autowired
//     // private RegistrationsService registrationsService;

//     private UUID uuid;
//     private String newModel;
//     private String newManufacturer;
//     // private UUID ownerUuid;
//     // private UUID firstEmergencyContactUuid;
//     // private UUID secondEmergencyContactUuid;

//     @Value("${server.port}")
//     private int randomServerPort;

//     WebClient webClient = WebClient.create("https://api.github.com");


//     @BeforeEach
//     public final void before() {

//         webClient = WebClient.create("https://localhost:" + randomServerPort);

//         var availableBeaconsUri = "/spring-api/beacons/";
//         var a =  webClient.get().uri(availableBeaconsUri).accept(MediaType.APPLICATION_JSON).retrieve()
//                         .bodyToMono(WrapperDTO.class).block().getData();


//         newModel = "the " + UUID.randomUUID() + " model";
//         newManufacturer = "the " + UUID.randomUUID() + " manufacturer";

//         final var payload = "{\"data\": {\"type\": \"beacons\",\"id\": \"1\",\"attributes\": {\"model\": \"New model value\",\"manufacturer\": \"New manufacturer value\"}}";
//     }

//     @Test
//     void requestAllBeaconControllerShouldReturnSomeBeacons() {
//         var original = makeGetRequest("/beacons/" + uuid);

//         var payload = "{\"data\": {\"type\": \"beacons\",\"id\": \"1\",\"attributes\": {\"model\": \"New model value\",\"manufacturer\": \"New manufacturer value\"}}";
//         var patch = makePatchRequest("/beacons/" + uuid, payload);

//         var updated = makeGetRequest("/beacons/" + uuid);


//         //request.jsonPath("$.data[0].type").isEqualTo("beacon");
//         updated.jsonPath("$.data[0].id").isEqualTo(uuid.toString());
//     }

//     @Test
//     void requestBeaconControllerShouldReturnBeaconByUuid() {
//     }

//     private WebTestClient.ResponseSpec makeGetRequest(String url) {
//         return webTestClient.get().uri(url).exchange().expectStatus().is2xxSuccessful();
//     }

//     private WebTestClient.ResponseSpec makePatchRequest(String url, String body) {
//         return webTestClient.patch().uri(url)
//         .bodyValue(body).exchange().expectStatus().is2xxSuccessful();
//     }
// }
