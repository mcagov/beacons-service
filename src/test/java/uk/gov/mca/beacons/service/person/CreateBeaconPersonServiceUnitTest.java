package uk.gov.mca.beacons.service.person;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.accounts.CreateAccountHolderService;
import uk.gov.mca.beacons.service.model.AccountHolder;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.repository.AccountHolderRepository;
import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CreateBeaconPersonServiceUnitTest {

  @Mock
  private BeaconPersonRepository beaconPersonRepository;

  @Test
  void shouldCreateTheAccountHolder() {
    final CreateBeaconPersonService createBeaconPersonService = new CreateBeaconPersonService(
            beaconPersonRepository
    );
    final BeaconPerson beaconPerson = new BeaconPerson();
    final BeaconPerson createdBeaconPerson = new BeaconPerson();

    assertThat(
            createBeaconPersonService.execute(beaconPerson),
      is(createdBeaconPerson)
    );
  }
}
