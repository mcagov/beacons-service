package uk.gov.mca.beacons.service.linkBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.hateoas.BeaconRolesService;
import uk.gov.mca.beacons.service.hateoas.HateoasLinkBuilder;
import uk.gov.mca.beacons.service.hateoas.HateoasLinkBuilder.SupportedMethod;
import uk.gov.mca.beacons.service.model.Beacon;

@ExtendWith(MockitoExtension.class)
class HateoasLinkBuilderTest {

  @Mock
  BeaconRolesService beaconRolesService;

  @Test
  void buildGetForBeaconShouldReturnExpectedLink() {
    var beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);
    var dto = new BeaconDTO();

    var linkBuilder = new HateoasLinkBuilder(beaconRolesService);
    linkBuilder.addLinkFor(beacon, SupportedMethod.GET, dto);

    assertThat(dto.getLinks().size(), is(1));
    assertThat(dto.getLinks().get(0).getVerb(), is("GET"));
    assertThat(
      dto.getLinks().get(0).getPath(),
      is("/beacons/" + beacon.getId())
    );
  }

  @Test
  void buildPatchForBeaconShouldReturnExpectedLink() {
    var beacon = new Beacon();
    var beaconId = UUID.randomUUID();
    beacon.setId(beaconId);
    var dto = new BeaconDTO();

    var userRoles = new ArrayList<String>();
    userRoles.add("APPROLE_UPDATE_RECORDS");
    given(beaconRolesService.getUserRoles()).willReturn(userRoles);

    var linkBuilder = new HateoasLinkBuilder(beaconRolesService);
    linkBuilder.addLinkFor(beacon, SupportedMethod.PATCH, dto);

    assertThat(dto.getLinks().size(), is(1));
    assertThat(dto.getLinks().get(0).getVerb(), is("PATCH"));
    assertThat(
      dto.getLinks().get(0).getPath(),
      is("/beacons/" + beacon.getId())
    );
  }
}
