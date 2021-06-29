package uk.gov.mca.beacons.api.controllers;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.mca.beacons.api.WebMvcTestConfiguration;
import uk.gov.mca.beacons.api.domain.PersonType;
import uk.gov.mca.beacons.api.dto.BeaconPersonDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.jpa.entities.Person;
import uk.gov.mca.beacons.api.mappers.BeaconPersonMapper;
import uk.gov.mca.beacons.api.services.CreateOwnerService;
import uk.gov.mca.beacons.api.services.GetPersonByIdService;

@WebMvcTest(controllers = OwnerController.class)
@AutoConfigureMockMvc
@Import(WebMvcTestConfiguration.class)
class OwnerControllerUnitTest {

    private final UUID beaconPersonId = UUID.fromString(
            "432e083d-7bd8-402b-9520-05da24ad143f"
    );

    private final Person owner = new Person();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateOwnerService createOwnerService;

    @MockBean
    private GetPersonByIdService getPersonByIdService;

    @MockBean
    private BeaconPersonMapper beaconPersonMapper;

    @BeforeEach
    public final void before() {
        owner.setId(beaconPersonId);
    }

    @Nested
    class RequestCreateOwner {

        @Test
        void shouldReturn201IfSuccessful() throws Exception {
            final WrapperDTO<BeaconPersonDTO> newBeaconPersonDTO = new WrapperDTO<>();
            final String newBeaconPersonRequest = new ObjectMapper()
                    .writeValueAsString(newBeaconPersonDTO);
            mvc
                    .perform(
                            post("/owner")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(newBeaconPersonRequest)
                    )
                    .andExpect(status().isCreated());
        }

        @Test
        void shouldReturn400IfInvalidJsonSent() throws Exception {
            final String createOwnerInvalidRequest = new String(
                    Files.readAllBytes(
                            Paths.get(
                                    "src/test/resources/fixtures/createOwnerInvalidRequest.json"
                            )
                    )
            );

            mvc
                    .perform(
                            post("/owner")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(createOwnerInvalidRequest)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors.length()", is(3)))
                    .andExpect(
                            jsonPath(
                                    "$.errors[*].field",
                                    hasItems(
                                            "data.attributes.fullName",
                                            "data.attributes.telephoneNumber",
                                            "data.attributes.email"
                                    )
                            )
                    );

            verify(createOwnerService, times(0)).execute(owner);
        }

        @Test
        void shouldMapDTOToDomainAccountHolder() throws Exception {
            final WrapperDTO<BeaconPersonDTO> newBeaconPersonDTO = new WrapperDTO<>();
            final String newBeaconPersonRequest = new ObjectMapper()
                    .writeValueAsString(newBeaconPersonDTO);

            mvc.perform(
                    post("/owner")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newBeaconPersonRequest)
            );

            verify(beaconPersonMapper, times(1))
                    .fromDTO(newBeaconPersonDTO.getData());
        }

        @Test
        void shouldCallTheAccountHolderServiceToCreateANewResource()
                throws Exception {
            final WrapperDTO<BeaconPersonDTO> newBeaconPersonDTO = new WrapperDTO<>();
            final String newBeaconPersonRequest = new ObjectMapper()
                    .writeValueAsString(newBeaconPersonDTO);
            given(beaconPersonMapper.fromDTO(newBeaconPersonDTO.getData()))
                    .willReturn(owner);

            mvc.perform(
                    post("/owner")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newBeaconPersonRequest)
            );

            verify(createOwnerService, times(1)).execute(owner);
        }
    }

    @Nested
    class RequestGetOwnerById {

        private final UUID ownerId = UUID.fromString("8f427a4a-65a9-48f8-b603-e3ff5506ffed");
        private final Person owner = Person.builder().id(ownerId).build();

        @Test
        void shouldRequestOwnerFromGetPersonByIdService() throws Exception {
            given(getPersonByIdService.execute(ownerId, PersonType.OWNER))
                    .willReturn(owner);

            mvc.perform(
                    get("/owners/" + ownerId)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            verify(getPersonByIdService, times(1)).execute(ownerId, PersonType.OWNER);
        }

//        @Test
//        void shouldReturn200WhenOwnerIdFound() throws Exception {
//            given(getPersonByIdService.execute(ownerId, PersonType.OWNER))
//                    .willReturn(owner);
//
//            mvc
//                    .perform(
//                            get("/account-holder/" + ownerId)
//                                    .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isOk());
//        }
//
//        @Test
//        void shouldMapOwnerToAWrapperDTO() throws Exception {
//            given(getPersonByIdService.execute(ownerId, PersonType.OWNER))
//                    .willReturn(owner);
//
//            mvc.perform(
//                    get("/account-holder/" + ownerId)
//                            .contentType(MediaType.APPLICATION_JSON)
//            );
//
//            verify(ownerMapper, times(1)).toWrapperDTO(owner);
//        }
//
//        @Test
//        void shouldReturn404IfOwnerNotFound() throws Exception {
//            given(getOwnerByAuthIdService.execute(authId)).willReturn(null);
//
//            mvc
//                    .perform(
//                            get("/account-holder/" + ownerId)
//                                    .contentType(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isNotFound());
//        }
    }
}
