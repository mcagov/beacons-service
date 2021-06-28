package uk.gov.mca.beacons.api.mappers;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static uk.gov.mca.beacons.api.domain.Environment.AVIATION;
import static uk.gov.mca.beacons.api.domain.Environment.MARITIME;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.Activity;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.jpa.entities.Beacon;
import uk.gov.mca.beacons.api.jpa.entities.BeaconUse;
import uk.gov.mca.beacons.api.jpa.entities.Person;

@ExtendWith(MockitoExtension.class)
class BeaconsRelationshipMapperTest {

    @Test
    void shouldReturnResultSetEvenWithoutUsesAndPersons() {
        final var firstBeaconId = UUID.randomUUID();
        final var firstBeacon = new Beacon();
        firstBeacon.setId(firstBeaconId);

        final var secondBeaconId = UUID.randomUUID();
        final var secondBeacon = new Beacon();
        secondBeacon.setId(secondBeaconId);

        final var mapper = new BeaconsRelationshipMapper();

        var allBeacons = mapper.getMappedBeacons(
                List.of(firstBeacon, secondBeacon),
                emptyList(),
                emptyList()
        );

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

        final var owner = new Person();
        owner.setPersonType(PersonType.OWNER);
        owner.setAddressLine1("My House");
        owner.setAddressLine2("My Street");
        owner.setFullName("Me Myself");
        owner.setBeaconId(testBeaconId);
        final var firstContact = new Person();
        firstContact.setPersonType(PersonType.EMERGENCY_CONTACT);
        firstContact.setFullName("Jean-Luc Picard");
        firstContact.setAddressLine1("The Bridge");
        firstContact.setAddressLine2("Enterprise");
        firstContact.setTelephoneNumber("NNC-1701-D");
        firstContact.setBeaconId(testBeaconId);
        final var secondContact = new Person();
        secondContact.setPersonType(PersonType.EMERGENCY_CONTACT);
        secondContact.setFullName("Bjorn Rune Borg");
        secondContact.setAddressLine1("Pod 13");
        secondContact.setAddressLine2("The Cube");
        secondContact.setBeaconId(testBeaconId);
        final var unrelatedContact = new Person();
        unrelatedContact.setPersonType(PersonType.EMERGENCY_CONTACT);
        unrelatedContact.setFullName("A Stranger");
        unrelatedContact.setAddressLine1("Shadowy Corner");
        unrelatedContact.setAddressLine2("Not Round Here");
        unrelatedContact.setBeaconId(UUID.randomUUID());
        var allPersons = List.of(
                owner,
                unrelatedContact,
                secondContact,
                firstContact
        );

        final var firstBeaconUse = new BeaconUse();
        firstBeaconUse.setActivity(Activity.HOT_AIR_BALLOON);
        firstBeaconUse.setEnvironment(AVIATION);
        firstBeaconUse.setMoreDetails("Neunundneunzig Luftballons");
        firstBeaconUse.setBeaconId(testBeaconId);
        final var secondBeaconUse = new BeaconUse();
        secondBeaconUse.setActivity(Activity.JET_AIRCRAFT);
        secondBeaconUse.setEnvironment(AVIATION);
        secondBeaconUse.setMoreDetails("Neunundneunzig Düsenflieger");
        secondBeaconUse.setBeaconId(testBeaconId);
        final var unrelatedBeaconUse = new BeaconUse();
        unrelatedBeaconUse.setActivity(Activity.WORKING_REMOTELY);
        unrelatedBeaconUse.setEnvironment(MARITIME);
        unrelatedBeaconUse.setMoreDetails("Lockdown Remote Worker");
        unrelatedBeaconUse.setBeaconId(UUID.randomUUID());
        var allUses = List.of(firstBeaconUse, unrelatedBeaconUse, secondBeaconUse);

        final var resultBeacon = new BeaconsRelationshipMapper()
                .getMappedBeacon(testBeacon, allPersons, allUses);

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
                        hasProperty("fullName", is("Bjorn Rune Borg")),
                        hasProperty("fullName", is("Jean-Luc Picard"))
                )
        );
    }

    @Test
    void shouldReturnZeroResultsIfNoBeacons() {
        final var mapper = new BeaconsRelationshipMapper();

        var allBeacons = mapper.getMappedBeacons(
                emptyList(),
                emptyList(),
                emptyList()
        );

        assertThat(allBeacons.size(), is(0));
    }
}
