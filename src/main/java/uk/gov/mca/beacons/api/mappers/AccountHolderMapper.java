package uk.gov.mca.beacons.api.mappers;

import static uk.gov.mca.beacons.api.dto.AccountHolderDTO.Attributes;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.dto.AccountHolderDTO;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.entities.AccountHolder;

@Service
public class AccountHolderMapper extends BaseMapper {

  public AccountHolder fromDTO(AccountHolderDTO accountHolderDTO) {
    final var accountHolder = new AccountHolder();
    accountHolder.setId(accountHolderDTO.getId());

    final var attributes = accountHolderDTO.getAttributes();

    accountHolder.setAuthId(attributes.getAuthId());
    accountHolder.setEmail(attributes.getEmail());
    accountHolder.setFullName(attributes.getFullName());
    accountHolder.setTelephoneNumber(attributes.getTelephoneNumber());
    accountHolder.setAlternativeTelephoneNumber(
      attributes.getAlternativeTelephoneNumber()
    );
    accountHolder.setAddressLine1(attributes.getAddressLine1());
    accountHolder.setAddressLine2(attributes.getAddressLine2());
    accountHolder.setAddressLine3(attributes.getAddressLine3());
    accountHolder.setAddressLine4(attributes.getAddressLine4());
    accountHolder.setTownOrCity(attributes.getTownOrCity());
    accountHolder.setPostcode(attributes.getPostcode());
    accountHolder.setCounty(attributes.getCounty());

    return accountHolder;
  }

  public CreateAccountHolderRequest toCreateAccountHolderRequest(
    AccountHolderDTO accountHolderDTO
  ) {
    final var attributes = accountHolderDTO.getAttributes();

    return CreateAccountHolderRequest
      .builder()
      .authId(attributes.getAuthId())
      .email(attributes.getEmail())
      .fullName(attributes.getFullName())
      .telephoneNumber(attributes.getTelephoneNumber())
      .alternativeTelephoneNumber(attributes.getAlternativeTelephoneNumber())
      .addressLine1(attributes.getAddressLine1())
      .addressLine2(attributes.getAddressLine2())
      .addressLine3(attributes.getAddressLine3())
      .addressLine4(attributes.getAddressLine4())
      .townOrCity(attributes.getTownOrCity())
      .postcode(attributes.getPostcode())
      .county(attributes.getCounty())
      .build();
  }

  public AccountHolderDTO toDTO(AccountHolder accountHolder) {
    final var dto = new AccountHolderDTO();
    dto.setId(accountHolder.getId());

    final Attributes attributes = Attributes
      .builder()
      .authId(accountHolder.getAuthId())
      .email(accountHolder.getEmail())
      .fullName(accountHolder.getFullName())
      .telephoneNumber(accountHolder.getTelephoneNumber())
      .alternativeTelephoneNumber(accountHolder.getAlternativeTelephoneNumber())
      .addressLine1(accountHolder.getAddressLine1())
      .addressLine2(accountHolder.getAddressLine2())
      .addressLine3(accountHolder.getAddressLine3())
      .addressLine4(accountHolder.getAddressLine4())
      .townOrCity(accountHolder.getTownOrCity())
      .postcode(accountHolder.getPostcode())
      .county(accountHolder.getCounty())
      .build();
    dto.setAttributes(attributes);

    return dto;
  }

  public final WrapperDTO<AccountHolderDTO> toWrapperDTO(
    AccountHolder accountHolder
  ) {
    final WrapperDTO<AccountHolderDTO> wrapperDTO = new WrapperDTO<>();

    wrapperDTO.setData(toDTO(accountHolder));

    return wrapperDTO;
  }
}
