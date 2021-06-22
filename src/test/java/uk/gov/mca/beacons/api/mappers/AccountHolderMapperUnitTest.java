package uk.gov.mca.beacons.api.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static uk.gov.mca.beacons.api.dto.AccountHolderDTO.Attributes;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.dto.AccountHolderDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.entities.AccountHolder;

@ExtendWith(MockitoExtension.class)
class AccountHolderMapperUnitTest {

    @Test
    void toDTO_shouldSetAllTheFieldsOnTheAccountHolderDTOFromTheDomain() {
        final var accountHolder = new AccountHolder();
        accountHolder.setId(
                UUID.fromString("461fc925-dbe0-448b-acf4-18727958393e")
        );
        accountHolder.setAuthId("a2fb6bb2-f735-41aa-a4a1-19cb951a51bc");
        accountHolder.setEmail("testy@mctestface.com");
        accountHolder.setFullName("Tesy McTestface");
        accountHolder.setTelephoneNumber("01178 657123");
        accountHolder.setAlternativeTelephoneNumber("");
        accountHolder.setAddressLine1("Flat 42");
        accountHolder.setAddressLine2("Testington Towers");
        accountHolder.setAddressLine3("");
        accountHolder.setAddressLine4("");
        accountHolder.setTownOrCity("Testville");
        accountHolder.setPostcode("TS1 23A");
        accountHolder.setCounty("Testershire");
        var accountHolderMapper = new AccountHolderMapper();

        final AccountHolderDTO accountHolderDTO = accountHolderMapper.toDTO(
                accountHolder
        );

        final var attributes = accountHolderDTO.getAttributes();

        assertThat(
                accountHolderDTO.getId(),
                is(UUID.fromString("461fc925-dbe0-448b-acf4-18727958393e"))
        );
        assertThat(
                attributes.getAuthId(),
                is("a2fb6bb2-f735-41aa-a4a1-19cb951a51bc")
        );
        assertThat(attributes.getEmail(), is("testy@mctestface.com"));
        assertThat(attributes.getFullName(), is("Tesy McTestface"));
        assertThat(attributes.getTelephoneNumber(), is("01178 657123"));
        assertThat(attributes.getAlternativeTelephoneNumber(), is(""));
        assertThat(attributes.getAddressLine1(), is("Flat 42"));
        assertThat(attributes.getAddressLine2(), is("Testington Towers"));
        assertThat(attributes.getAddressLine3(), is(""));
        assertThat(attributes.getAddressLine4(), is(""));
        assertThat(attributes.getTownOrCity(), is("Testville"));
        assertThat(attributes.getPostcode(), is("TS1 23A"));
        assertThat(attributes.getCounty(), is("Testershire"));
    }

    @Test
    void fromDTO_shouldInstantiateADomainObjectFromTheDTO() {
        var accountHolderDTO = new AccountHolderDTO();
        accountHolderDTO.setId(
                UUID.fromString("461fc925-dbe0-448b-acf4-18727958393e")
        );

        final var attributes = Attributes
                .builder()
                .authId("a2fb6bb2-f735-41aa-a4a1-19cb951a51bc")
                .email("testy@mctestface.com")
                .fullName("Tesy McTestface")
                .telephoneNumber("01178 657123")
                .alternativeTelephoneNumber("")
                .addressLine1("Flat 42")
                .addressLine2("Testington Towers")
                .addressLine3("")
                .addressLine4("")
                .townOrCity("Testville")
                .postcode("TS1 23A")
                .county("Testershire")
                .build();

        accountHolderDTO.setAttributes(attributes);
        var accountHolderMapper = new AccountHolderMapper();

        AccountHolder accountHolder = accountHolderMapper.fromDTO(accountHolderDTO);

        assertThat(
                accountHolder.getId(),
                is(UUID.fromString("461fc925-dbe0-448b-acf4-18727958393e"))
        );
        assertThat(
                accountHolder.getAuthId(),
                is("a2fb6bb2-f735-41aa-a4a1-19cb951a51bc")
        );
        assertThat(accountHolder.getEmail(), is("testy@mctestface.com"));
        assertThat(accountHolder.getFullName(), is("Tesy McTestface"));
        assertThat(accountHolder.getTelephoneNumber(), is("01178 657123"));
        assertThat(accountHolder.getAlternativeTelephoneNumber(), is(""));
        assertThat(accountHolder.getAddressLine1(), is("Flat 42"));
        assertThat(accountHolder.getAddressLine2(), is("Testington Towers"));
        assertThat(accountHolder.getAddressLine3(), is(""));
        assertThat(accountHolder.getAddressLine4(), is(""));
        assertThat(accountHolder.getTownOrCity(), is("Testville"));
        assertThat(accountHolder.getPostcode(), is("TS1 23A"));
        assertThat(accountHolder.getCounty(), is("Testershire"));
    }

    @Test
    void toWrapperDTO_shouldConvertAnAccountHolderToAWrappedDTO() {
        final var accountHolder = new AccountHolder();
        final var accountHolderMapper = new AccountHolderMapper();

        final WrapperDTO<AccountHolderDTO> wrappedAccountHolder = accountHolderMapper.toWrapperDTO(
                accountHolder
        );

        assertNotNull(wrappedAccountHolder.getData());
        assertNotNull(wrappedAccountHolder.getIncluded());
        assertNotNull(wrappedAccountHolder.getMeta());
    }
}
