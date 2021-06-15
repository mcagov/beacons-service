package uk.gov.mca.beacons.service.mappers;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.model.AccountHolder;

@Service
public class AccountHolderMapper extends BaseMapper {

  public AccountHolder fromDTO(AccountHolderDTO accountHolderDTO) {
    return null;
  }

  public AccountHolderDTO toDTO(AccountHolder accountHolder) {
    final var dto = new AccountHolderDTO();
    dto.setId(accountHolder.getId());
    dto.addAttribute("authId", accountHolder.getAuthId());
    dto.addAttribute("email", accountHolder.getEmail());
    dto.addAttribute("fullName", accountHolder.getFullName());
    dto.addAttribute("telephoneNumber", accountHolder.getTelephoneNumber());
    dto.addAttribute(
            "alternativeTelephoneNumber",
            accountHolder.getAlternativeTelephoneNumber()
    );
    dto.addAttribute("addressLine1", accountHolder.getAddressLine1());
    dto.addAttribute("addressLine2", accountHolder.getAddressLine2());
    dto.addAttribute("addressLine3", accountHolder.getAddressLine3());
    dto.addAttribute("addressLine4", accountHolder.getAddressLine4());
    dto.addAttribute("townOrCity", accountHolder.getTownOrCity());
    dto.addAttribute("postcode", accountHolder.getPostcode());
    dto.addAttribute("county", accountHolder.getCounty());

    return dto;
  }
}
