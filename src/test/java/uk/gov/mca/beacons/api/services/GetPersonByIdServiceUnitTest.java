package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.gateways.PersonGateway;
import uk.gov.mca.beacons.api.jpa.entities.Person;

@ExtendWith(MockitoExtension.class)
class GetPersonByIdServiceUnitTest {

  @Mock
  private PersonGateway mockPersonGateway;

  @Mock
  private Person mockPerson;

  @Test
  void getById_shouldReturnNullIfNotFound() {
    GetPersonByIdService getPersonByIdService = new GetPersonByIdService(
      mockPersonGateway
    );
    UUID nonExistentPersonId = UUID.randomUUID();
    given(mockPersonGateway.getById(nonExistentPersonId)).willReturn(null);

    assertNull(getPersonByIdService.execute(nonExistentPersonId));
  }

  @Test
  void getById_shouldReturnThePerson() {
    GetPersonByIdService getPersonByIdService = new GetPersonByIdService(
      mockPersonGateway
    );
    UUID existingPersonId = UUID.randomUUID();
    given(mockPersonGateway.getById(existingPersonId)).willReturn(mockPerson);

    Person foundPerson = getPersonByIdService.execute(existingPersonId);

    assertThat(foundPerson, is(mockPerson));
  }
}
