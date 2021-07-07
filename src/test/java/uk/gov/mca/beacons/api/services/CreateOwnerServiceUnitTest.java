package uk.gov.mca.beacons.api.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.dto.CreateOwnerRequest;
import uk.gov.mca.beacons.api.gateways.OwnerGateway;
import uk.gov.mca.beacons.api.jpa.entities.Person;

@ExtendWith(MockitoExtension.class)
class CreateOwnerServiceUnitTest {

  @Mock
  private OwnerGateway ownerGateway;

  @Test
  void shouldCreateTheAccountHolder() {
    final CreateOwnerService createOwnerService = new CreateOwnerService(
      ownerGateway
    );
    final Person person = new Person();
    final Person createdPerson = new Person();

    given(ownerGateway.create(isA(CreateOwnerRequest.class)))
      .willReturn(createdPerson);

    assertThat(createOwnerService.execute(person), is(createdPerson));
  }
}
