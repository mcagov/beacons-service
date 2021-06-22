package uk.gov.mca.beacons.service.accounts;

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
import uk.gov.mca.beacons.service.beacons.BeaconsResponseFactory;
import uk.gov.mca.beacons.service.domain.AccountHolder;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.AccountHolderIdDTO;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.mappers.AccountHolderMapper;

@RestController
@RequestMapping("/account-holder")
@Tag(name = "Account Holder")
public class AccountHolderController {

  private final AccountHolderMapper accountHolderMapper;

  private final GetAccountHolderByIdService getAccountHolderByIdService;

  private final GetAccountHolderByAuthIdService getAccountHolderByAuthIdService;

  private final CreateAccountHolderService createAccountHolderService;

  private final GetBeaconsByAccountHolderIdService getBeaconsByAccountHolderIdService;

  private final BeaconsResponseFactory responseFactory;

  @Autowired
  public AccountHolderController(
    AccountHolderMapper accountHolderMapper,
    GetAccountHolderByIdService getAccountHolderByIdService,
    GetAccountHolderByAuthIdService getAccountHolderByAuthIdService,
    CreateAccountHolderService createAccountHolderService,
    GetBeaconsByAccountHolderIdService getBeaconsByAccountHolderIdService,
    BeaconsResponseFactory responseFactory
  ) {
    this.accountHolderMapper = accountHolderMapper;
    this.getAccountHolderByIdService = getAccountHolderByIdService;
    this.getAccountHolderByAuthIdService = getAccountHolderByAuthIdService;
    this.createAccountHolderService = createAccountHolderService;
    this.getBeaconsByAccountHolderIdService =
      getBeaconsByAccountHolderIdService;
    this.responseFactory = responseFactory;
  }

  @GetMapping(value = "/{id}")
  public WrapperDTO<AccountHolderDTO> getAccountHolder(
    @PathVariable("id") UUID id
  ) {
    final AccountHolder accountHolder = getAccountHolderByIdService.execute(id);

    if (accountHolder == null) throw new ResourceNotFoundException();

    return accountHolderMapper.toWrapperDTO(accountHolder);
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
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<AccountHolderDTO> createAccountHolder(
    @RequestBody WrapperDTO<AccountHolderDTO> dto
  ) {
    final CreateAccountHolderRequest newAccountHolderRequest = accountHolderMapper.toCreateAccountHolderRequest(
      dto.getData()
    );

    return accountHolderMapper.toWrapperDTO(
      createAccountHolderService.execute(newAccountHolderRequest)
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
