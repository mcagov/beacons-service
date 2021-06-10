package uk.gov.mca.beacons.service.accounts;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.service.dto.AccountHolderIdDTO;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.model.AccountHolder;

@RestController
@RequestMapping("/account-holder")
@Tag(name = "Account Holder")
public class AccountHolderController {

  private final GetAccountHolderByAuthIdService getAccountHolderByAuthIdService;

  @Autowired
  public AccountHolderController(
    GetAccountHolderByAuthIdService getAccountHolderByAuthIdService
  ) {
    this.getAccountHolderByAuthIdService = getAccountHolderByAuthIdService;
  }

  @GetMapping(value = "/auth-id/{authId}")
  public AccountHolderIdDTO getAccountHolderId(
    @PathVariable("authId") String authId
  ) {
    final AccountHolder accountHolder = getAccountHolderByAuthIdService.execute(
      authId
    );

    if (accountHolder == null) throw new ResourceNotFoundException();

    return new AccountHolderIdDTO(accountHolder.getId());
  }

  @PostMapping
  public void createAccountHolder() {

  }
}
