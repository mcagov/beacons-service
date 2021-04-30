package uk.gov.mca.beacons.service.registrations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.Activity;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.PersonType;
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

  @Test
  void shouldReturnDeepResult() {
    final var testBeaconId = UUID.randomUUID();
    final var testBeacon = new Beacon();
    testBeacon.setId(testBeaconId);
    given(beaconRepository.findAll()).willReturn(List.of(testBeacon));

    final var owner = new BeaconPerson();
    owner.setPersonType(PersonType.OWNER);
    owner.setAddressLine1("My House");
    owner.setAddressLine2("My Street");
    owner.setFullName("Me Myself");
    owner.setBeaconId(testBeaconId);
    final var firstContact = new BeaconPerson();
    firstContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    firstContact.setFullName("Jean-Luc Picard");
    firstContact.setAddressLine1("The Bridge");
    firstContact.setAddressLine2("Enterprise");
    firstContact.setTelephoneNumber("NNC-1701-D");
    firstContact.setBeaconId(testBeaconId);
    final var secondContact = new BeaconPerson();
    secondContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    secondContact.setFullName("Bjorn Rune Borg");
    secondContact.setAddressLine1("Pod 13");
    secondContact.setAddressLine2("The Cube");
    secondContact.setBeaconId(testBeaconId);
    final var unrelatedContact = new BeaconPerson();
    unrelatedContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    unrelatedContact.setFullName("A Stranger");
    unrelatedContact.setAddressLine1("Shadowy Corner");
    unrelatedContact.setAddressLine2("Not Round Here");
    unrelatedContact.setBeaconId(UUID.randomUUID());
    given(beaconPersonRepository.findAll())
      .willReturn(
        List.of(firstContact, unrelatedContact, owner, secondContact)
      );

    final var firstBeaconUse = new BeaconUse();
    firstBeaconUse.setActivity(Activity.HOT_AIR_BALLOON);
    firstBeaconUse.setEnvironment(
      uk.gov.mca.beacons.service.model.Environment.AVIATION
    );
    firstBeaconUse.setMoreDetails("Neunundneunzig Luftballons");
    firstBeaconUse.setBeaconId(testBeaconId);
    final var secondBeaconUse = new BeaconUse();
    secondBeaconUse.setActivity(Activity.JET_AIRCRAFT);
    secondBeaconUse.setEnvironment(
      uk.gov.mca.beacons.service.model.Environment.AVIATION
    );
    secondBeaconUse.setMoreDetails("Neunundneunzig Düsenflieger");
    secondBeaconUse.setBeaconId(testBeaconId);
    final var unrelatedBeaconUse = new BeaconUse();
    unrelatedBeaconUse.setActivity(Activity.WORKING_REMOTELY);
    unrelatedBeaconUse.setEnvironment(
      uk.gov.mca.beacons.service.model.Environment.OTHER
    );
    unrelatedBeaconUse.setMoreDetails("Lockdown Remote Worker");
    unrelatedBeaconUse.setBeaconId(UUID.randomUUID());
    given(beaconUseRepository.findAll())
      .willReturn(List.of(firstBeaconUse, unrelatedBeaconUse, secondBeaconUse));

    final var getAllBeaconService = new GetAllBeaconsService(
      beaconRepository,
      beaconUseRepository,
      beaconPersonRepository
    );
    final var allBeacons = getAllBeaconService.findAll();

    final var resultBeacon = allBeacons.get(0);
    assertThat("one beacon is returned", allBeacons.size(), is(1));
    assertThat(
      "the one beacon has the expected id",
      resultBeacon,
      hasProperty("id", is(testBeaconId))
    );
    assertThat(
      "the beacon has the correct owner",
      resultBeacon.getOwner().getFullName(),
      is("Me Myself")
    );
    final var resultUses = resultBeacon.getUses();
    assertThat("the beacon has only 2 uses mapped", resultUses.size(), is(2));
    assertThat(
      "the uses are the ones expected",
      resultUses,
      contains(
        hasProperty("moreDetails", is("Neunundneunzig Luftballons")),
        hasProperty("moreDetails", is("Neunundneunzig Düsenflieger"))
      )
    );
    final var resultContacts = resultBeacon.getEmergencyContacts();
    assertThat(
      "the beacon has only 2 contacts mapped",
      resultContacts.size(),
      is(2)
    );
    assertThat(
      "the contacts are the expected ones",
      resultContacts,
      contains(
        hasProperty("fullName", is("Jean-Luc Picard")),
        hasProperty("fullName", is("Bjorn Rune Borg"))
      )
    );
  }

  @Test
  void shouldReturnABeaconById() {
    final var firstBeaconId = UUID.randomUUID();
    final var firstBeacon = new Beacon();
    firstBeacon.setId(firstBeaconId);

    final var secondBeaconId = UUID.randomUUID();
    final var secondBeacon = new Beacon();
    secondBeacon.setId(secondBeaconId);

    given(beaconRepository.findById(firstBeaconId))
      .willReturn(Optional.of(firstBeacon));
    given(beaconRepository.findById(secondBeaconId))
      .willReturn(Optional.of(secondBeacon));

    final var getAllBeaconService = new GetAllBeaconsService(
      beaconRepository,
      beaconUseRepository,
      beaconPersonRepository
    );

    Beacon firstBeaconOnly = getAllBeaconService
      .find(firstBeaconId)
      .orElse(new Beacon());
    Beacon secondBeaconOnly = getAllBeaconService
      .find(secondBeaconId)
      .orElse(new Beacon());

    assertThat(firstBeaconOnly, hasProperty("id", is(firstBeaconId)));
    assertThat(secondBeaconOnly, hasProperty("id", is(secondBeaconId)));
  }

  @Test
  void shouldReturnZeroResultsIfIdNotFound() {
    final var noExistentBeaconId = UUID.randomUUID();

    final var getAllBeaconService = new GetAllBeaconsService(
      beaconRepository,
      beaconUseRepository,
      beaconPersonRepository
    );
    final var beacon = getAllBeaconService.find(noExistentBeaconId);

    assertThat(beacon, is(Optional.empty()));
  }
}
