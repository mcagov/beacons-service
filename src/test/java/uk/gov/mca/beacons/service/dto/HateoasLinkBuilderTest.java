package uk.gov.mca.beacons.service.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.dto.HateoasLinkBuilder.SupportedMethod;
import uk.gov.mca.beacons.service.model.Beacon;

@ExtendWith(MockitoExtension.class)
class HateoasLinkBuilderTest {

  @Test
  void buildGetForBeaconShouldReturnExpectedLink() {
    var beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);
    var dto = new BeaconDTO();

    var linkBuilder = new HateoasLinkBuilder();
    linkBuilder.addLinkFor(beacon, SupportedMethod.GET, dto);

    assertThat(dto.getLinks().get("GET"), is("/beacons/" + beacon.getId()));
  }

  @Test
  void buildPatchForBeaconShouldReturnExpectedLink() {
    var beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);
    var dto = new BeaconDTO();

    var linkBuilder = new HateoasLinkBuilder();
    linkBuilder.addLinkFor(beacon, SupportedMethod.PATCH, dto);

    assertThat(dto.getLinks().get("PATCH"), is("/beacons/" + beacon.getId()));
  }
}
