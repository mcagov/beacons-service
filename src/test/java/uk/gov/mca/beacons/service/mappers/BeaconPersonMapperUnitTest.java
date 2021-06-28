package uk.gov.mca.beacons.service.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import uk.gov.mca.beacons.service.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.service.model.BeaconPerson;

class BeaconPersonMapperUnitTest {

  @Test
  void shouldMapAllFields() {
    final var beaconPersonMapper = new BeaconPersonMapper();
    final BeaconPerson person = new BeaconPerson();
    final UUID personId = UUID.randomUUID();
    final UUID beaconId = UUID.randomUUID();
    final String fullName = "Phoebe Buffay";
    final String telephoneNumber = "07777777777";
    final String telephoneNumber2 = "07888888888";
    final String alternativeTelephoneNumber = "07888888889";
    final String alternativeTelephoneNumber2 = "07888888810";
    final String email = "phoebe@buffay.com";
    final String addressLine1 = "1 Apartment";
    final String addressLine2 = "Building";
    final String addressLine3 = "Street";
    final String addressLine4 = "Borough";
    final String townOrCity = "New York";
    final String postcode = "A1 2BC";
    final String county = "Big Apple";
    final String country = "UK";
    final String companyName = "MCA";
    final String careOf = "Me";
    final String fax = "Fax me";
    final String isMain = "Y";
    final Integer createUserId = 0;
    final Integer updateUserId = 1;
    final Integer versioning = 10;

    person.setId(personId);
    person.setBeaconId(UUID.randomUUID());
    person.setFullName(fullName);
    person.setTelephoneNumber(telephoneNumber);
    person.setTelephoneNumber2(telephoneNumber2);
    person.setAlternativeTelephoneNumber(alternativeTelephoneNumber);
    person.setAlternativeTelephoneNumber2(alternativeTelephoneNumber2);
    person.setEmail(email);
    person.setAddressLine1(addressLine1);
    person.setAddressLine2(addressLine2);
    person.setAddressLine3(addressLine3);
    person.setAddressLine4(addressLine4);
    person.setTownOrCity(townOrCity);
    person.setPostcode(postcode);
    person.setCounty(county);
    person.setCountry(country);
    person.setCompanyName(companyName);
    person.setCareOf(careOf);
    person.setFax(fax);
    person.setIsMain(isMain);
    person.setCreateUserId(createUserId);
    person.setUpdateUserId(updateUserId);
    person.setVersioning(versioning);

    BeaconPersonDTO personDTO = beaconPersonMapper.toDTO(person);
    final var attributes = personDTO.getAttributes();

    assertThat(personDTO.getId(), is(personId));
    assertThat(attributes.getFullName(), is(fullName));
    assertThat(attributes.getTelephoneNumber(), is(telephoneNumber));
    assertThat(attributes.getTelephoneNumber2(), is(telephoneNumber2));
    assertThat(
      attributes.getAlternativeTelephoneNumber(),
      is(alternativeTelephoneNumber)
    );
    assertThat(
      attributes.getAlternativeTelephoneNumber2(),
      is(alternativeTelephoneNumber2)
    );
    assertThat(attributes.getEmail(), is(email));
    assertThat(attributes.getAddressLine1(), is(addressLine1));
    assertThat(attributes.getAddressLine2(), is(addressLine2));
    assertThat(attributes.getAddressLine3(), is(addressLine3));
    assertThat(attributes.getAddressLine4(), is(addressLine4));
    assertThat(attributes.getTownOrCity(), is(townOrCity));
    assertThat(attributes.getPostcode(), is(postcode));
    assertThat(attributes.getCounty(), is(county));
    assertThat(attributes.getCountry(), is(country));
    assertThat(attributes.getCompanyName(), is(companyName));
    assertThat(attributes.getCareOf(), is(careOf));
    assertThat(attributes.getFax(), is(fax));
    assertThat(attributes.getIsMain(), is(isMain));
    assertThat(attributes.getCreateUserId(), is(createUserId));
    assertThat(attributes.getUpdateUserId(), is(updateUserId));
    assertThat(attributes.getVersioning(), is(versioning));
  }
}
