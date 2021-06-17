package uk.gov.mca.beacons.service.accounts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.hateoas.BeaconLinkStrategy;
import uk.gov.mca.beacons.service.hateoas.HateoasLinkManager;
import uk.gov.mca.beacons.service.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.model.Beacon;

@ExtendWith(MockitoExtension.class)
class AccountHolderMapperUnitTest {

  @Mock
  private HateoasLinkManager<Beacon> linkManager;

  @Mock
  private BeaconLinkStrategy linkStrategy;

  @Test
  void toDTO_shouldSetAllTheFieldsOnTheAccountHolderDTOFromTheDomain() {
    var accountHolder = new AccountHolder();
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

    AccountHolderDTO accountHolderDTO = accountHolderMapper.toDTO(
      accountHolder
    );

    assertThat(
      accountHolderDTO.getId(),
      is(UUID.fromString("461fc925-dbe0-448b-acf4-18727958393e"))
    );
    assertThat(
      accountHolderDTO.getAttributes().get("authId"),
      is("a2fb6bb2-f735-41aa-a4a1-19cb951a51bc")
    );
    assertThat(
      accountHolderDTO.getAttributes().get("email"),
      is("testy@mctestface.com")
    );
    assertThat(
      accountHolderDTO.getAttributes().get("fullName"),
      is("Tesy McTestface")
    );
    assertThat(
      accountHolderDTO.getAttributes().get("telephoneNumber"),
      is("01178 657123")
    );
    assertThat(
      accountHolderDTO.getAttributes().get("alternativeTelephoneNumber"),
      is("")
    );
    assertThat(
      accountHolderDTO.getAttributes().get("addressLine1"),
      is("Flat 42")
    );
    assertThat(
      accountHolderDTO.getAttributes().get("addressLine2"),
      is("Testington Towers")
    );
    assertThat(accountHolderDTO.getAttributes().get("addressLine3"), is(""));
    assertThat(accountHolderDTO.getAttributes().get("addressLine4"), is(""));
    assertThat(
      accountHolderDTO.getAttributes().get("townOrCity"),
      is("Testville")
    );
    assertThat(accountHolderDTO.getAttributes().get("postcode"), is("TS1 23A"));
    assertThat(
      accountHolderDTO.getAttributes().get("county"),
      is("Testershire")
    );
  }

  @Test
  void fromDTO_shouldInstantiateADomainObjectFromTheDTO() {
    var accountHolderDTO = new AccountHolderDTO();
    accountHolderDTO.setId(
      UUID.fromString("461fc925-dbe0-448b-acf4-18727958393e")
    );

    final var attributes = new HashMap<String, Object>();
    attributes.put("authId", "a2fb6bb2-f735-41aa-a4a1-19cb951a51bc");
    attributes.put("email", "testy@mctestface.com");
    attributes.put("fullName", "Tesy McTestface");
    attributes.put("telephoneNumber", "01178 657123");
    attributes.put("alternativeTelephoneNumber", "");
    attributes.put("addressLine1", "Flat 42");
    attributes.put("addressLine2", "Testington Towers");
    attributes.put("addressLine3", "");
    attributes.put("addressLine4", "");
    attributes.put("townOrCity", "Testville");
    attributes.put("postcode", "TS1 23A");
    attributes.put("county", "Testershire");
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
