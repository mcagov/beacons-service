package uk.gov.mca.beacons.api.accountholder.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.gov.mca.beacons.api.accountholder.application.AccountHolderService;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolder;
import uk.gov.mca.beacons.api.accountholder.domain.AccountHolderId;
import uk.gov.mca.beacons.api.accountholder.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;

@RestController("AccountHolderControllerV2")
@RequestMapping("/spring-api/account-holderv2")
@Tag(name = "Account Holder v2")
public class AccountHolderController {

  private final AccountHolderService accountHolderService;
  private final AccountHolderMapper accountHolderMapper;

  @Autowired
  public AccountHolderController(
    AccountHolderService accountHolderService,
    AccountHolderMapper accountHolderMapper
  ) {
    this.accountHolderMapper = accountHolderMapper;
    this.accountHolderService = accountHolderService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<AccountHolderDTO> createAccountHolder(
    @RequestBody WrapperDTO<AccountHolderDTO> wrapperDTO
  ) {
    final AccountHolder accountHolder = accountHolderMapper.fromDTO(
      wrapperDTO.getData()
    );
    final AccountHolder createdAccountHolder = accountHolderService.create(
      accountHolder
    );

    return accountHolderMapper.toWrapperDTO(createdAccountHolder);
  }

  @GetMapping(value = "/{id}")
  public WrapperDTO<AccountHolderDTO> getAccountHolder(
    @PathVariable("id") UUID id
  ) {
    final AccountHolderId accountHolderId = new AccountHolderId(id);
    final AccountHolder accountHolder = accountHolderService
      .getAccountHolder(accountHolderId)
      .orElseThrow(ResourceNotFoundException::new);

    return accountHolderMapper.toWrapperDTO(accountHolder);
  }

  @GetMapping
  public WrapperDTO<AccountHolderDTO> getAccountHolderByAuthId(
    @RequestParam(name = "authId") String authId
  ) {
    final AccountHolder accountHolder = accountHolderService
      .getAccountHolderByAuthId(authId)
      .orElseThrow(ResourceNotFoundException::new);

    return accountHolderMapper.toWrapperDTO(accountHolder);
  }
}
