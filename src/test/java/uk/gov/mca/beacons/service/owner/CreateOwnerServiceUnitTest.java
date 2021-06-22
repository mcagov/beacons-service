package uk.gov.mca.beacons.service.person;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.gateway.OwnerGateway;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.owner.CreateOwnerService;

@ExtendWith(MockitoExtension.class)
class CreateOwnerServiceUnitTest {

  @Mock
  private OwnerGateway ownerGateway;

  @Test
  void shouldCreateTheAccountHolder() {
    final CreateOwnerService createOwnerService = new CreateOwnerService(
      ownerGateway
    );
    final BeaconPerson beaconPerson = new BeaconPerson();
    final BeaconPerson createdBeaconPerson = new BeaconPerson();

    assertThat(
      createOwnerService.execute(beaconPerson),
      is(createdBeaconPerson)
    );
  }
}
