package uk.gov.mca.beacons.service.mappers;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.AccountHolder;

@Service
public class AccountHolderMapper extends BaseMapper {

  public AccountHolder fromDTO(AccountHolderDTO accountHolderDTO) {
      final var accountHolder = new AccountHolder();
      accountHolder.setId(accountHolderDTO.getId());

      accountHolder.setAuthId(
              (String) accountHolderDTO.getAttributes().get("authId")
      );
      accountHolder.setEmail(
              (String) accountHolderDTO.getAttributes().get("email")
      );
      accountHolder.setFullName(
              (String) accountHolderDTO.getAttributes().get("fullName")
      );
      accountHolder.setTelephoneNumber(
              (String) accountHolderDTO.getAttributes().get("telephoneNumber")
      );
      accountHolder.setAlternativeTelephoneNumber(
              (String) accountHolderDTO
                      .getAttributes()
                      .get("alternativeTelephoneNumber")
      );
      accountHolder.setAddressLine1(
              (String) accountHolderDTO.getAttributes().get("addressLine1")
      );
      accountHolder.setAddressLine2(
              (String) accountHolderDTO.getAttributes().get("addressLine2")
      );
      accountHolder.setAddressLine3(
              (String) accountHolderDTO.getAttributes().get("addressLine3")
      );
      accountHolder.setAddressLine4(
              (String) accountHolderDTO.getAttributes().get("addressLine4")
      );
      accountHolder.setTownOrCity(
              (String) accountHolderDTO.getAttributes().get("townOrCity")
      );
      accountHolder.setPostcode(
              (String) accountHolderDTO.getAttributes().get("postcode")
      );
      accountHolder.setCounty(
              (String) accountHolderDTO.getAttributes().get("county")
      );

      return accountHolder;
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

    public WrapperDTO<AccountHolderDTO> toWrapperDTO(
            AccountHolder accountHolder
    ) {
        WrapperDTO<AccountHolderDTO> wrapperDTO = new WrapperDTO<>();

        wrapperDTO.setData(toDTO(accountHolder));

        return wrapperDTO;
    }
}
