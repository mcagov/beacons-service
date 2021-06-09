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

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    void requestAccountHolderIdByAuthId_shouldRequestAccountHolderIdFromServiceByAuthId() throws Exception {
        given(accountService
                .getId("04c4dbf3-ca7c-4df9-98b6-fb2ccf422526"))
                .willReturn(UUID.fromString("ab54f98a-6906-4a2d-b897-663990a602fa"));

        mvc.perform(get("/account-holder/auth-id/04c4dbf3-ca7c-4df9-98b6-fb2ccf422526")
                .contentType(MediaType.APPLICATION_JSON));

        verify(accountService, times(1)).getId("04c4dbf3-ca7c-4df9-98b6-fb2ccf422526");
    }

    @Test
    void requestAccountHolderIdByAuthId_shouldReturn200WhenAccountHolderIdFound() throws Exception {
        given(accountService
                .getId("04c4dbf3-ca7c-4df9-98b6-fb2ccf422526"))
                .willReturn(UUID.fromString("ab54f98a-6906-4a2d-b897-663990a602fa"));

        mvc.perform(
                get("/account-holder/auth-id/04c4dbf3-ca7c-4df9-98b6-fb2ccf422526")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void requestAccountHolderIdByAuthId_shouldReturnTheAccountHolderId() throws Exception {
        String authId = "04c4dbf3-ca7c-4df9-98b6-fb2ccf422526";
        UUID accountHolderId = UUID.fromString("ab54f98a-6906-4a2d-b897-663990a602fa");
        given(accountService
                .getId(authId))
                .willReturn(accountHolderId);

        String responseBody = mvc.perform(get("/account-holder/auth-id/" + authId)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedBody = String.format("{\"id\":\"%s\"}", accountHolderId);

        assertThat(responseBody, equalTo(expectedBody));
    }

    @Configuration
    @ComponentScan(basePackageClasses = { AccountsController.class })
    public static class TestConf {}
}
