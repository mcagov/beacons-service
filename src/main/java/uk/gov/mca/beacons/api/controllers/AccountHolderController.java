package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.domain.AccountHolder;
import uk.gov.mca.beacons.api.dto.AccountHolderDTO;
import uk.gov.mca.beacons.api.dto.AccountHolderIdDTO;
import uk.gov.mca.beacons.api.dto.BeaconDTO;
import uk.gov.mca.beacons.api.dto.CreateAccountHolderRequest;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.api.mappers.BeaconsResponseFactory;
import uk.gov.mca.beacons.api.services.AccountHolderService;
import uk.gov.mca.beacons.api.services.GetBeaconsByAccountHolderIdService;

@RestController
@RequestMapping("/account-holder")
@Tag(name = "Account Holder")
public class AccountHolderController {

  private final AccountHolderMapper accountHolderMapper;

  private final AccountHolderService accountHolderService;

  private final GetBeaconsByAccountHolderIdService getBeaconsByAccountHolderIdService;

  private final BeaconsResponseFactory responseFactory;

  @Autowired
  public AccountHolderController(
    AccountHolderMapper accountHolderMapper,
    AccountHolderService accountHolderService,
    GetBeaconsByAccountHolderIdService getBeaconsByAccountHolderIdService,
    BeaconsResponseFactory responseFactory
  ) {
    this.accountHolderMapper = accountHolderMapper;
    this.accountHolderService = accountHolderService;
    this.getBeaconsByAccountHolderIdService =
      getBeaconsByAccountHolderIdService;
    this.responseFactory = responseFactory;
  }

  @GetMapping(value = "/{id}")
  public WrapperDTO<AccountHolderDTO> getAccountHolder(
    @PathVariable("id") UUID id
  ) {
    final AccountHolder accountHolder = accountHolderService.getById(id);

    if (accountHolder == null) throw new ResourceNotFoundException();

    return accountHolderMapper.toWrapperDTO(accountHolder);
  }

  @GetMapping(value = "/auth-id/{authId}")
  public AccountHolderIdDTO getAccountHolderId(
    @PathVariable("authId") String authId
  ) {
    final AccountHolder accountHolder = accountHolderService.getByAuthId(
      authId
    );

    if (accountHolder == null) throw new ResourceNotFoundException();

    return new AccountHolderIdDTO(accountHolder.getId());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<AccountHolderDTO> createAccountHolder(
    @RequestBody WrapperDTO<AccountHolderDTO> dto
  ) {
    final CreateAccountHolderRequest newAccountHolderRequest = accountHolderMapper.toCreateAccountHolderRequest(
      dto.getData()
    );

    return accountHolderMapper.toWrapperDTO(
      accountHolderService.create(newAccountHolderRequest)
    );
  }

  @GetMapping(value = "/{accountId}/beacons")
  public ResponseEntity<WrapperDTO<List<BeaconDTO>>> getBeaconsByAccountHolderId(
    @PathVariable("accountId") UUID accountId
  ) {
    final var beacons = getBeaconsByAccountHolderIdService.execute(accountId);
    return ResponseEntity.ok(responseFactory.buildDTO(beacons));
  }
}
