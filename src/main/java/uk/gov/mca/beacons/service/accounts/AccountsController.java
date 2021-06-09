package uk.gov.mca.beacons.service.accounts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.dto.AccountHolderIdDTO;


@RestController
@RequestMapping("/account-holder")
@Tag(name = "Account Holder")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping(value = "/auth-id/{authId}")
    public AccountHolderIdDTO getAccountHolder(@PathVariable("authId") String authId) {
        String accountHolderId = accountsService.getId(authId).toString();

        ObjectMapper objectMapper = new ObjectMapper();

        return new AccountHolderIdDTO(accountHolderId);
    }
}
