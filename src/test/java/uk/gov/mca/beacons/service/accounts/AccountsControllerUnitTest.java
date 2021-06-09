package uk.gov.mca.beacons.service.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AccountsController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class AccountsControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountsService accountService;

    @Test
    void requestAccountHolderByAuthId_shouldReturnAnAccountHolder() throws Exception {
        AccountHolderDTO accountHolderDto = new AccountHolderDTO();
        accountHolderDto.addAttribute("id", "ab54f98a-6906-4a2d-b897-663990a602fa");
        accountHolderDto.addAttribute("authId", "04c4dbf3-ca7c-4df9-98b6-fb2ccf422526");
        accountHolderDto.addAttribute("email", "accounty@mcaccountface.com");
        accountHolderDto.addAttribute("fullName", "Accounty McAccountface");
        accountHolderDto.addAttribute("phoneNumber", "01282 616641");
        accountHolderDto.addAttribute("addressLine1", "42 Accounts Road");
        accountHolderDto.addAttribute("townOrCity", "Accountsville");
        accountHolderDto.addAttribute("postcode", "AC1 1AA");

        given(accountService.getAccountHolderByAuthId("04c4dbf3-ca7c-4df9-98b6-fb2ccf422526")).willReturn(accountHolderDto);

        mvc.perform(get("/account-holder/auth-id/04c4dbf3-ca7c-4df9-98b6-fb2ccf422526")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Configuration
    @ComponentScan(basePackageClasses = { AccountsController.class })
    public static class TestConf {}
}
