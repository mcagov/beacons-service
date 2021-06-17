package uk.gov.mca.beacons.service.mappers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.AccountHolder;

@Service
public class AccountHolderMapper extends BaseMapper {

  public AccountHolder fromDTO(AccountHolderDTO accountHolderDTO) {
    final var accountHolder = new AccountHolder();
    accountHolder.setId(accountHolderDTO.getId());

    final var attributes = accountHolderDTO.getAttributes();

    accountHolder.setAuthId((String) attributes.get("authId"));
    accountHolder.setEmail((String) attributes.get("email"));
    accountHolder.setFullName((String) attributes.get("fullName"));
    accountHolder.setTelephoneNumber(
      (String) attributes.get("telephoneNumber")
    );
    accountHolder.setAlternativeTelephoneNumber(
      (String) attributes.get("alternativeTelephoneNumber")
    );
    accountHolder.setAddressLine1((String) attributes.get("addressLine1"));
    accountHolder.setAddressLine2((String) attributes.get("addressLine2"));
    accountHolder.setAddressLine3((String) attributes.get("addressLine3"));
    accountHolder.setAddressLine4((String) attributes.get("addressLine4"));
    accountHolder.setTownOrCity((String) attributes.get("townOrCity"));
    accountHolder.setPostcode((String) attributes.get("postcode"));
    accountHolder.setCounty((String) attributes.get("county"));

    return accountHolder;
  }

  public AccountHolderDTO toDTO(AccountHolder accountHolder) {
    final var dto = new AccountHolderDTO();
    dto.setId(accountHolder.getId());

    final Map<String, Object> attributes = new HashMap<>();
    attributes.put("authId", accountHolder.getAuthId());
    attributes.put("email", accountHolder.getEmail());
    attributes.put("fullName", accountHolder.getFullName());
    attributes.put("telephoneNumber", accountHolder.getTelephoneNumber());
    attributes.put(
      "alternativeTelephoneNumber",
      accountHolder.getAlternativeTelephoneNumber()
    );
    attributes.put("addressLine1", accountHolder.getAddressLine1());
    attributes.put("addressLine2", accountHolder.getAddressLine2());
    attributes.put("addressLine3", accountHolder.getAddressLine3());
    attributes.put("addressLine4", accountHolder.getAddressLine4());
    attributes.put("townOrCity", accountHolder.getTownOrCity());
    attributes.put("postcode", accountHolder.getPostcode());
    attributes.put("county", accountHolder.getCounty());
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
