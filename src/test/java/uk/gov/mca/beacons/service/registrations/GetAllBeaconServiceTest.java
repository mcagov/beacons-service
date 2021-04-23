package uk.gov.mca.beacons.service.registrations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@ExtendWith(MockitoExtension.class)
class GetAllBeaconServiceTest {

  @Mock
  private BeaconRepository beaconRepository;

  @Mock
  private BeaconPersonRepository beaconPersonRepository;

  @Mock
  private BeaconUseRepository beaconUseRepository;

  @BeforeEach
  void init() {}

  @Test
  void shouldReturnZeroResults() {
    final var getAllBeaconService = new GetAllBeaconsService(
      beaconRepository,
      beaconUseRepository,
      beaconPersonRepository
    );
    final var beacons = getAllBeaconService.findAll();

    assertThat(beacons, is(emptyCollectionOf(Beacon.class)));
  }

  @Test
  void shouldReturnResultSet() {
    final var firstBeaconId = UUID.randomUUID();
    final var firstBeacon = new Beacon();
    firstBeacon.setId(firstBeaconId);

    final var secondBeaconId = UUID.randomUUID();
    final var secondBeacon = new Beacon();
    secondBeacon.setId(secondBeaconId);

    given(beaconRepository.findAll())
      .willReturn(List.of(firstBeacon, secondBeacon));

    final var getAllBeaconService = new GetAllBeaconsService(
      beaconRepository,
      beaconUseRepository,
      beaconPersonRepository
    );
    final var allBeacons = getAllBeaconService.findAll();

    assertThat(
      allBeacons,
      contains(
        hasProperty("id", is(firstBeaconId)),
        hasProperty("id", is(secondBeaconId))
      )
    );
  }
  // @Ignore @Test
  // void shouldReturnDeepResult() {
  //     final var beaconId = UUID.randomUUID();
  //     final var beacon = new Beacon();
  //     beacon.setId(beaconId);
  //     final var owner = new BeaconPerson();
  //     final var beaconUse = new BeaconUse();
  //     final var emergencyContact = new BeaconPerson();

  //     given(beaconRepository.findAll()).willReturn(List.of(beacon));

  //     final var getAllBeaconService = new GetAllBeaconService(beaconRepository, null, null);
  //     final var returnedOwner = getAllBeaconService.findAll().get(0);

  //     assertThat(returnedOwner.getOwner(), is(owner));

  // }

}
