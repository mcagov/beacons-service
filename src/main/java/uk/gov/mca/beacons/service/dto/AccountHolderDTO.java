package uk.gov.mca.beacons.service.dto;

import javax.validation.constraints.Email;

public class AccountHolderDTO
  extends DomainDTO<AccountHolderDTO.AccountHolderAttributes> {

  @Override
  public String getType() {
    return "accountHolder";
  }

  static class AccountHolderAttributes {

    @Email
    private String email;
  }
}
