package uk.gov.mca.beacons.service.uses;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.service.db.BeaconUse;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.exceptions.InvalidPatchException;
import uk.gov.mca.beacons.service.mappers.BeaconUseMapper;

@ExtendWith(MockitoExtension.class)
class BeaconUsesControllerUnitTest {

    @InjectMocks
    private BeaconUsesController beaconUsesController;

    @Mock
    private BeaconUseMapper beaconUseMapper;

    @Mock
    private BeaconUsesPatchService beaconUsesPatchService;

    @Test
    void shouldCallThroughToTheBeaconsBeaconsUsesService() {
        final var id = UUID.randomUUID();
        final var beaconUseDto = new BeaconUseDTO();
        beaconUseDto.setId(id);

        final var dto = new WrapperDTO<BeaconUseDTO>();
        dto.setData(beaconUseDto);
        final var beaconUse = new BeaconUse();

        given(beaconUseMapper.fromDTO(dto.getData())).willReturn(beaconUse);

        beaconUsesController.update(id, dto);

        then(beaconUsesPatchService).should().update(id, beaconUse);
    }

    @Test
    void shouldThrowAnErrorIfTheIdWithinTheJsonDoesNotMatchThePathVariable() {
        var id = UUID.randomUUID();
        var beaconUseDto = new BeaconUseDTO();
        beaconUseDto.setId(UUID.randomUUID());

        var dto = new WrapperDTO<BeaconUseDTO>();
        dto.setData(beaconUseDto);

        assertThrows(
                InvalidPatchException.class,
                () -> {
                    beaconUsesController.update(id, dto);
                }
        );
    }
}
