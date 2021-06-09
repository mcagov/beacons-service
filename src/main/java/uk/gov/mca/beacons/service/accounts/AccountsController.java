package uk.gov.mca.beacons.service.accounts;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;

import java.util.UUID;

@RestController
@RequestMapping("/account-holder")
@Tag(name = "Account Holder")
public class AccountsController {

    @GetMapping(value = "/auth-id/{uuid}")
    public AccountHolderDTO getAccountHolder(String uuid) {

        return new AccountHolderDTO();
    }
}
