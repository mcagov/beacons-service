package uk.gov.mca.beacons.service.gateway;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.db.Person;
import uk.gov.mca.beacons.service.entities.PersonType;
import uk.gov.mca.beacons.service.jpa.BeaconPersonJpaRepository;

@ExtendWith(MockitoExtension.class)
class OwnerGatewayImplUnitTest {

    @InjectMocks
    private OwnerGatewayImpl ownerGateway;

    @Mock
    private BeaconPersonJpaRepository beaconPersonJpaRepository;

    @Captor
    private ArgumentCaptor<Person> ownerCaptor;

    @Test
    void shouldCreateAnOwnerFromRequestObject() {
        final UUID beaconId = UUID.randomUUID();
        final String fullName = "Souma Matveev";
        final String telephoneNumber = "07777777777";
        final String alternativeTelephoneNumber = "02088888888";
        final String email = "Souma.Matveev@aol.com";
        final String addressLine1 = "1 Street";
        final String addressLine2 = "Kanto";
        final String addressLine3 = "Johto";
        final String addressLine4 = "Sinnoh";
        final String townOrCity = "Lawndale";
        final String postcode = "A1 2BC";
        final String county = "Devonhevonshire";
        final CreateOwnerRequest createOwnerRequest = CreateOwnerRequest
                .builder()
                .beaconId(beaconId)
                .fullName(fullName)
                .telephoneNumber(telephoneNumber)
                .alternativeTelephoneNumber(alternativeTelephoneNumber)
                .email(email)
                .addressLine1(addressLine1)
                .addressLine2(addressLine2)
                .addressLine3(addressLine3)
                .addressLine4(addressLine4)
                .townOrCity(townOrCity)
                .postcode(postcode)
                .county(county)
                .build();

        ownerGateway.save(createOwnerRequest);

        verify(beaconPersonJpaRepository).save(ownerCaptor.capture());
        final Person owner = ownerCaptor.getValue();

        assertThat(owner.getBeaconId(), is(beaconId));
        assertThat(owner.getFullName(), is(fullName));
        assertThat(owner.getTelephoneNumber(), is(telephoneNumber));
        assertThat(
                owner.getAlternativeTelephoneNumber(),
                is(alternativeTelephoneNumber)
        );
        assertThat(owner.getEmail(), is(email));
        assertThat(owner.getPersonType(), is(PersonType.OWNER));
        assertThat(owner.getAddressLine1(), is(addressLine1));
        assertThat(owner.getAddressLine2(), is(addressLine2));
        assertThat(owner.getAddressLine3(), is(addressLine3));
        assertThat(owner.getAddressLine4(), is(addressLine4));
        assertThat(owner.getTownOrCity(), is(townOrCity));
        assertThat(owner.getPostcode(), is(postcode));
        assertThat(owner.getCounty(), is(county));
    }
}
