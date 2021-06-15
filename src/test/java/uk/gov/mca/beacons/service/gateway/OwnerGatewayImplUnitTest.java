package uk.gov.mca.beacons.service.gateway;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.PersonType;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;

@ExtendWith(MockitoExtension.class)
class OwnerGatewayImplUnitTest {

  @InjectMocks
  private OwnerGatewayImpl ownerGateway;

  @Mock
  private BeaconPersonRepository beaconPersonRepository;

  @Mock
  private BeaconRepository beaconRepository;

  @Captor
  private ArgumentCaptor<BeaconPerson> ownerCaptor;

  @Test
  void shouldCreateAnOwnerFromRequestObject() {
    Beacon beacon = new Beacon();
    UUID beaconId = beacon.getId();
    given(beaconRepository.findById(beaconId)).willReturn(Optional.of(beacon));

    final CreateOwnerRequest createOwnerRequest = CreateOwnerRequest
      .builder()
      .fullName("Hello World")
      .postcode("A1 2BC")
      .beaconId(beaconId)
      .build();

    ownerGateway.save(createOwnerRequest);
    verify(beaconRepository, times(1)).findById(beaconId);
    verify(beaconPersonRepository).save(ownerCaptor.capture());
    BeaconPerson owner = ownerCaptor.getValue();
    assertThat(owner.getFullName(), is("Hello World"));
    assertThat(owner.getPostcode(), is("A1 2BC"));
    assertThat(owner.getPersonType(), is(PersonType.OWNER));
    assertThat(owner.getBeaconId(), is(beaconId));
  }
}
