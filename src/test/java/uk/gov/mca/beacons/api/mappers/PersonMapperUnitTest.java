package uk.gov.mca.beacons.api.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.api.entities.PersonType;

class PersonMapperUnitTest {

    @Test
    void shouldMapAllFields() {
        final var beaconPersonMapper = new BeaconPersonMapper();
        final Person person = new Person();
        final UUID personId = UUID.randomUUID();
        final UUID beaconId = UUID.randomUUID();
        final String fullName = "Phoebe Buffay";
        final String telephoneNumber = "07777777777";
        final String alternativeTelephoneNumber = "07888888888";
        final String email = "phoebe@buffay.com";
        final PersonType personType = PersonType.OWNER;
        final String addressLine1 = "1 Apartment";
        final String addressLine2 = "Building";
        final String addressLine3 = "Street";
        final String addressLine4 = "Borough";
        final String townOrCity = "New York";
        final String postcode = "A1 2BC";
        final String county = "Big Apple";

        person.setId(personId);
        person.setBeaconId(UUID.randomUUID());
        person.setFullName(fullName);
        person.setTelephoneNumber(telephoneNumber);
        person.setAlternativeTelephoneNumber(alternativeTelephoneNumber);
        person.setEmail(email);
        person.setAddressLine1(addressLine1);
        person.setAddressLine2(addressLine2);
        person.setAddressLine3(addressLine3);
        person.setAddressLine4(addressLine4);
        person.setTownOrCity(townOrCity);
        person.setPostcode(postcode);
        person.setCounty(county);

        BeaconPersonDTO personDTO = beaconPersonMapper.toDTO(person);
        final var attributes = personDTO.getAttributes();

        assertThat(personDTO.getId(), is(personId));
        assertThat(attributes.get("fullName"), is(fullName));
        assertThat(attributes.get("telephoneNumber"), is(telephoneNumber));
        assertThat(
                attributes.get("alternativeTelephoneNumber"),
                is(alternativeTelephoneNumber)
        );
        assertThat(attributes.get("email"), is(email));
        assertThat(attributes.get("addressLine1"), is(addressLine1));
        assertThat(attributes.get("addressLine2"), is(addressLine2));
        assertThat(attributes.get("addressLine3"), is(addressLine3));
        assertThat(attributes.get("addressLine4"), is(addressLine4));
        assertThat(attributes.get("townOrCity"), is(townOrCity));
        assertThat(attributes.get("postcode"), is(postcode));
        assertThat(attributes.get("county"), is(county));
    }
}
