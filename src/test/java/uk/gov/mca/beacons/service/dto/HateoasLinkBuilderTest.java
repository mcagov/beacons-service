package uk.gov.mca.beacons.service.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.Beacon;

@ExtendWith(MockitoExtension.class)
class HateoasLinkBuilderTest {

  @Test
  void buildGetForBeaconShouldReturnExpectedLink() {
    var beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);

    var linkBuilder = new HateoasLinkBuilder();
    var result = linkBuilder.buildGetFor(beacon);

    assertThat(result, is("/beacons/" + beacon.getId()));
  }

  @Test
  void buildPatchForBeaconShouldReturnExpectedLink() {
    var beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);

    var linkBuilder = new HateoasLinkBuilder();
    var result = linkBuilder.buildPatchFor(beacon);

    assertThat(result, is("/beacons/" + beacon.getId()));
  }
}
