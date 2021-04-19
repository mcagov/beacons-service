package uk.gov.mca.beacons.service.registrations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.empty;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.mca.beacons.service.repository.BeaconPersonRepository;
import uk.gov.mca.beacons.service.repository.BeaconRepository;
import uk.gov.mca.beacons.service.repository.BeaconUseRepository;

@ExtendWith(MockitoExtension.class)
class GetAllBeaconServiceTest {

    @Mock
    private BeaconRepository beaconRepository;
  
    @Mock
    private BeaconPersonRepository beaconPersonRepository;
  
    @Mock
    private BeaconUseRepository beaconUseRepository;

    @BeforeEach
    void init() {
    }

    @Test
    void shouldReturnZeroBeacons() throws Exception {  
        final var getAllBeaconService = new GetAllBeaconService(beaconRepository, beaconUseRepository, beaconPersonRepository) ;
        final var allBeacons = getAllBeaconService.findAll();

        assertThat(allBeacons, is(empty()));
    }

}
