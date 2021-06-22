package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.db.Beacon;
import uk.gov.mca.beacons.api.db.BeaconUse;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.entities.PersonType;
import uk.gov.mca.beacons.api.jpa.BeaconJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconPersonJpaRepository;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.mappers.BeaconsRelationshipMapper;
import uk.gov.mca.beacons.api.mappers.ModelPatcherFactory;

@ExtendWith(MockitoExtension.class)
class GetBeaconsByIdUnitTest {

  @Mock
  private BeaconJpaRepository beaconJpaRepository;

  @Mock
  private BeaconPersonJpaRepository beaconPersonJpaRepository;

  @Mock
  private BeaconUseJpaRepository beaconUseJpaRepository;

  @Mock
  private ModelPatcherFactory<Beacon> patcherFactory;

  private BeaconsService beaconsService;

  @BeforeEach
  void before() {
    beaconsService =
      new BeaconsService(
        beaconJpaRepository,
        beaconPersonJpaRepository,
        beaconUseJpaRepository,
        new BeaconsRelationshipMapper(),
        patcherFactory
      );
  }

  @Test
  void findByIdShouldReturnNullResultsIfIdNotFound() {
    final var noExistentBeaconId = UUID.randomUUID();

    final var beacon = beaconsService.find(noExistentBeaconId);

    assertNull(beacon);
  }

  @Test
  void findByIdShouldReturnOneBeaconById() {
    final var firstBeaconId = UUID.randomUUID();
    final var firstBeacon = new Beacon();
    firstBeacon.setId(firstBeaconId);

    final var secondBeaconId = UUID.randomUUID();
    final var secondBeacon = new Beacon();
    secondBeacon.setId(secondBeaconId);

    given(beaconJpaRepository.findById(firstBeaconId))
      .willReturn(Optional.of(firstBeacon));
    given(beaconJpaRepository.findById(secondBeaconId))
      .willReturn(Optional.of(secondBeacon));

    Beacon firstBeaconOnly = beaconsService.find(firstBeaconId);
    Beacon secondBeaconOnly = beaconsService.find(secondBeaconId);

    assertThat(firstBeaconOnly, hasProperty("id", is(firstBeaconId)));
    assertThat(secondBeaconOnly, hasProperty("id", is(secondBeaconId)));
  }

  @Test
  void findByIdShouldReturnMappedSingleResult() {
    final var testBeaconId = AssembleTestData();

    final var resultBeacon = beaconsService.find(testBeaconId);

    assertThat(
      "the one beacon has the expected id",
      resultBeacon,
      hasProperty("id", is(testBeaconId))
    );
    assertThat(
      "the beacon has an owner",
      resultBeacon.getOwner(),
      is(notNullValue())
    );
    assertThat(
      "the beacon has only 2 uses mapped",
      resultBeacon.getUses().size(),
      is(2)
    );
    assertThat(
      "the beacon has only 2 contacts mapped",
      resultBeacon.getEmergencyContacts().size(),
      is(2)
    );
  }

  @Test
  void findByIdShouldReturnABeaconByIdEvenWithMissingData() {
    final var testBeaconId = AssembleFaultyTestData();

    final var resultBeacon = beaconsService.find(testBeaconId);

    assertThat(
      "the one beacon has the expected id",
      resultBeacon.getId(),
      is(testBeaconId)
    );
    assertThat(
      "the beacon has no owner",
      resultBeacon.getOwner(),
      is(nullValue())
    );
    assertThat(
      "the beacon has no uses mapped",
      resultBeacon.getUses().size(),
      is(0)
    );
    assertThat(
      "the beacon has no contacts mapped",
      resultBeacon.getEmergencyContacts().size(),
      is(0)
    );
  }

  private UUID AssembleTestData() {
    final var testBeaconId = UUID.randomUUID();
    final Beacon testBeacon = new Beacon();
    testBeacon.setId(testBeaconId);
    given(beaconJpaRepository.findById(testBeaconId))
      .willReturn(Optional.of(testBeacon));

    final var owner = new Person();
    owner.setPersonType(PersonType.OWNER);
    owner.setFullName("Me Myself");
    owner.setBeaconId(testBeaconId);
    final var firstContact = new Person();
    firstContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    firstContact.setFullName("Jean-Luc Picard");
    firstContact.setBeaconId(testBeaconId);
    final var secondContact = new Person();
    secondContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    secondContact.setFullName("Bjorn Rune Borg");
    secondContact.setBeaconId(testBeaconId);
    final var unrelatedContact = new Person();
    unrelatedContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    unrelatedContact.setFullName("A Stranger");
    unrelatedContact.setBeaconId(UUID.randomUUID());
    given(beaconPersonJpaRepository.findAllByBeaconId(testBeaconId))
      .willReturn(
        List.of(firstContact, unrelatedContact, owner, secondContact)
      );

    final var firstBeaconUse = new BeaconUse();
    firstBeaconUse.setMoreDetails("Neunundneunzig Luftballons");
    firstBeaconUse.setBeaconId(testBeaconId);
    final var secondBeaconUse = new BeaconUse();
    secondBeaconUse.setMoreDetails("Neunundneunzig Düsenflieger");
    secondBeaconUse.setBeaconId(testBeaconId);
    final var unrelatedBeaconUse = new BeaconUse();
    unrelatedBeaconUse.setMoreDetails("Lockdown Remote Worker");
    unrelatedBeaconUse.setBeaconId(UUID.randomUUID());
    given(beaconUseJpaRepository.findAllByBeaconId(testBeaconId))
      .willReturn(List.of(firstBeaconUse, unrelatedBeaconUse, secondBeaconUse));
    return testBeaconId;
  }

  private UUID AssembleFaultyTestData() {
    final var testBeaconId = UUID.randomUUID();
    final var testBeacon = new Beacon();
    testBeacon.setId(testBeaconId);
    given(beaconJpaRepository.findById(testBeaconId))
      .willReturn(Optional.of(testBeacon));

    final var owner = new Person();
    owner.setPersonType(PersonType.OWNER);
    owner.setFullName("Me Myself");
    owner.setBeaconId(UUID.randomUUID());
    final var firstContact = new Person();
    firstContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    firstContact.setFullName("Jean-Luc Picard");
    firstContact.setBeaconId(UUID.randomUUID());
    final var secondContact = new Person();
    secondContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    secondContact.setFullName("Bjorn Rune Borg");
    secondContact.setBeaconId(UUID.randomUUID());
    final var unrelatedContact = new Person();
    unrelatedContact.setPersonType(PersonType.EMERGENCY_CONTACT);
    unrelatedContact.setFullName("A Stranger");
    unrelatedContact.setBeaconId(UUID.randomUUID());
    given(beaconPersonJpaRepository.findAllByBeaconId(testBeaconId))
      .willReturn(
        List.of(firstContact, unrelatedContact, owner, secondContact)
      );

    final var firstBeaconUse = new BeaconUse();
    firstBeaconUse.setMoreDetails("Neunundneunzig Luftballons");
    firstBeaconUse.setBeaconId(UUID.randomUUID());
    final var secondBeaconUse = new BeaconUse();
    secondBeaconUse.setMoreDetails("Neunundneunzig Düsenflieger");
    secondBeaconUse.setBeaconId(UUID.randomUUID());
    final var unrelatedBeaconUse = new BeaconUse();
    unrelatedBeaconUse.setMoreDetails("Lockdown Remote Worker");
    unrelatedBeaconUse.setBeaconId(UUID.randomUUID());
    given(beaconUseJpaRepository.findAllByBeaconId(testBeaconId))
      .willReturn(List.of(firstBeaconUse, unrelatedBeaconUse, secondBeaconUse));
    return testBeaconId;
  }
}
