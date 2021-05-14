package uk.gov.mca.beacons.service.beacons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.exceptions.InvalidPatchException;
import uk.gov.mca.beacons.service.mappers.BeaconMapper;
import uk.gov.mca.beacons.service.model.Beacon;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BeaconsControllerUnitTest {

    @InjectMocks
    private BeaconsController beaconsController;

    @Mock
    private BeaconsService beaconsService;

    @Mock
    private BeaconMapper beaconMapper;

    @Nested
    class BeaconPatchUpdate {
        private WrapperDTO<BeaconDTO> dto;
        private BeaconDTO beaconDTO;
        private UUID beaconId;

        @BeforeEach
        void init() {
            dto = new WrapperDTO<>();
            beaconDTO = new BeaconDTO();
            dto.setData(beaconDTO);
            beaconId = UUID.randomUUID();
            beaconDTO.setId(beaconId);
        }

        @Test
        void shouldCallThroughToTheBeaconsServiceIfAValidPatchRequest() {
            final var beaconToUpdate = new Beacon();
            given(beaconMapper.fromDTO(beaconDTO)).willReturn(beaconToUpdate);

            beaconsController.update(beaconId, dto);

            then(beaconsService).should(times(1)).update(beaconId, beaconToUpdate);
        }

        @Test
        void shouldThrowAnErrorIfTheIdWithinTheJsonDoesNotMatchThePathVariable() {
            final WrapperDTO<BeaconDTO> dto = new WrapperDTO<>();
            final BeaconDTO beaconDTO = new BeaconDTO();
            dto.setData(beaconDTO);
            beaconDTO.setId(UUID.randomUUID());

            assertThrows(InvalidPatchException.class, () -> {
                beaconsController.update(UUID.randomUUID(), dto);
            });
        }
    }
}
