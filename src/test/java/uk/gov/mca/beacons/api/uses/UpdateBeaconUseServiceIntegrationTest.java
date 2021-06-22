package uk.gov.mca.beacons.api.uses;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import uk.gov.mca.beacons.api.db.BeaconUse;
import uk.gov.mca.beacons.api.entities.Activity;
import uk.gov.mca.beacons.api.entities.Environment;
import uk.gov.mca.beacons.api.entities.Purpose;
import uk.gov.mca.beacons.api.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.api.jpa.BeaconUseJpaRepository;
import uk.gov.mca.beacons.api.services.UpdateBeaconUseService;

@SpringBootTest
class UpdateBeaconUseServiceIntegrationTest {

    @Autowired
    private UpdateBeaconUseService updateBeaconUseService;

    @MockBean
    private BeaconUseJpaRepository beaconUseJpaRepository;

    @Test
    void shouldThrowAnExceptionIfTheBeaconUseIsNotFound() {
        var id = UUID.randomUUID();
        given(beaconUseJpaRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> updateBeaconUseService.update(id, new BeaconUse())
        );
    }

    @Test
    void shouldUpdateAllTheFieldsOnTheBeaconUse() {
        var id = UUID.randomUUID();

        var existingBeacon = new BeaconUse();

        var beaconToUpdate = new BeaconUse();
        beaconToUpdate.setEnvironment(Environment.MARITIME);
        beaconToUpdate.setPurpose(Purpose.PLEASURE);
        beaconToUpdate.setActivity(Activity.SAILING);
        beaconToUpdate.setCallSign("call him");
        beaconToUpdate.setVhfRadio(true);
        beaconToUpdate.setFixedVhfRadio(true);
        beaconToUpdate.setFixedVhfRadioValue("+117");
        beaconToUpdate.setPortableVhfRadio(true);
        beaconToUpdate.setPortableVhfRadioValue("+127");
        beaconToUpdate.setSatelliteTelephone(true);
        beaconToUpdate.setSatelliteTelephoneValue("+137");
        beaconToUpdate.setMobileTelephone(true);
        beaconToUpdate.setMobileTelephone1("+147");
        beaconToUpdate.setMobileTelephone2("+157");
        beaconToUpdate.setOtherCommunication(true);
        beaconToUpdate.setOtherCommunicationValue("+167");
        beaconToUpdate.setMaxCapacity(1);
        beaconToUpdate.setVesselName("Mary");
        beaconToUpdate.setPortLetterNumber("1");
        beaconToUpdate.setHomeport("2");
        beaconToUpdate.setAreaOfOperation("London");
        beaconToUpdate.setBeaconLocation("Bristol");
        beaconToUpdate.setImoNumber("10");
        beaconToUpdate.setSsrNumber("20");
        beaconToUpdate.setRssNumber("30");
        beaconToUpdate.setOfficialNumber("40");
        beaconToUpdate.setRigPlatformLocation("Manchester");
        beaconToUpdate.setMainUse(true);
        beaconToUpdate.setAircraftManufacturer("Boeing");
        beaconToUpdate.setPrincipalAirport("Glasgow");
        beaconToUpdate.setSecondaryAirport("Cardiff");
        beaconToUpdate.setRegistrationMark("Reg 1");
        beaconToUpdate.setCnOrMsnNumber("1");
        beaconToUpdate.setDongle(false);
        beaconToUpdate.setBeaconPosition("North");
        beaconToUpdate.setWorkingRemotelyLocation("Bristol");
        beaconToUpdate.setWorkingRemotelyPeopleCount("5");
        beaconToUpdate.setWindfarmPeopleCount("2");
        beaconToUpdate.setOtherActivityLocation("Newcastle");
        beaconToUpdate.setOtherActivityPeopleCount("5");
        beaconToUpdate.setMoreDetails("More details");

        given(beaconUseJpaRepository.findById(id))
                .willReturn(Optional.of(existingBeacon));

        updateBeaconUseService.update(id, beaconToUpdate);

        var argumentCapture = ArgumentCaptor.forClass(BeaconUse.class);
        then(beaconUseJpaRepository).should(times(1)).save(argumentCapture.capture());

        var updatedBeaconUse = argumentCapture.getValue();
        assertThat(updatedBeaconUse.getEnvironment(), is(Environment.MARITIME));
        assertThat(updatedBeaconUse.getPurpose(), is(Purpose.PLEASURE));
        assertThat(updatedBeaconUse.getActivity(), is(Activity.SAILING));
        assertThat(updatedBeaconUse.getCallSign(), is("call him"));
        assertTrue(updatedBeaconUse.getVhfRadio());
        assertTrue(updatedBeaconUse.getFixedVhfRadio());
        assertThat(updatedBeaconUse.getFixedVhfRadioValue(), is("+117"));
        assertTrue(updatedBeaconUse.getPortableVhfRadio());
        assertThat(updatedBeaconUse.getPortableVhfRadioValue(), is("+127"));
        assertTrue(updatedBeaconUse.getSatelliteTelephone());
        assertThat(updatedBeaconUse.getSatelliteTelephoneValue(), is("+137"));
        assertTrue(updatedBeaconUse.getMobileTelephone());
        assertThat(updatedBeaconUse.getMobileTelephone1(), is("+147"));
        assertThat(updatedBeaconUse.getMobileTelephone2(), is("+157"));
        assertTrue(updatedBeaconUse.getOtherCommunication());
        assertThat(updatedBeaconUse.getOtherCommunicationValue(), is("+167"));
        assertThat(updatedBeaconUse.getMaxCapacity(), is(1));
        assertThat(updatedBeaconUse.getVesselName(), is("Mary"));
        assertThat(updatedBeaconUse.getPortLetterNumber(), is("1"));
        assertThat(updatedBeaconUse.getHomeport(), is("2"));
        assertThat(updatedBeaconUse.getAreaOfOperation(), is("London"));
        assertThat(updatedBeaconUse.getBeaconLocation(), is("Bristol"));
        assertThat(updatedBeaconUse.getImoNumber(), is("10"));
        assertThat(updatedBeaconUse.getSsrNumber(), is("20"));
        assertThat(updatedBeaconUse.getRssNumber(), is("30"));
        assertThat(updatedBeaconUse.getOfficialNumber(), is("40"));
        assertThat(updatedBeaconUse.getRigPlatformLocation(), is("Manchester"));
        assertTrue(updatedBeaconUse.getMainUse());
        assertThat(updatedBeaconUse.getAircraftManufacturer(), is("Boeing"));
        assertThat(updatedBeaconUse.getPrincipalAirport(), is("Glasgow"));
        assertThat(updatedBeaconUse.getSecondaryAirport(), is("Cardiff"));
        assertThat(updatedBeaconUse.getRegistrationMark(), is("Reg 1"));
        assertThat(updatedBeaconUse.getCnOrMsnNumber(), is("1"));
        assertFalse(updatedBeaconUse.getDongle());
        assertThat(updatedBeaconUse.getBeaconPosition(), is("North"));
        assertThat(updatedBeaconUse.getWorkingRemotelyLocation(), is("Bristol"));
        assertThat(updatedBeaconUse.getWorkingRemotelyPeopleCount(), is("5"));
        assertThat(updatedBeaconUse.getWindfarmPeopleCount(), is("2"));
        assertThat(updatedBeaconUse.getOtherActivityLocation(), is("Newcastle"));
        assertThat(updatedBeaconUse.getMoreDetails(), is("More details"));
    }

    @Test
    void shouldPartiallyUpdateTheBeaconUse() {
        var id = UUID.randomUUID();

        var existingBeacon = new BeaconUse();
        existingBeacon.setEnvironment(Environment.MARITIME);
        existingBeacon.setActivity(Activity.FISHING_VESSEL);
        existingBeacon.setMainUse(true);
        existingBeacon.setMaxCapacity(5);
        existingBeacon.setVhfRadio(true);
        existingBeacon.setVesselName("Mary");

        var beaconToUpdate = new BeaconUse();
        beaconToUpdate.setActivity(Activity.SAILING);
        beaconToUpdate.setMaxCapacity(10);
        beaconToUpdate.setVhfRadio(false);
        beaconToUpdate.setVesselName("Andy");

        given(beaconUseJpaRepository.findById(id))
                .willReturn(Optional.of(existingBeacon));

        updateBeaconUseService.update(id, beaconToUpdate);

        var argumentCapture = ArgumentCaptor.forClass(BeaconUse.class);
        then(beaconUseJpaRepository).should(times(1)).save(argumentCapture.capture());

        var updatedBeaconUse = argumentCapture.getValue();
        assertThat(updatedBeaconUse.getEnvironment(), is(Environment.MARITIME));
        assertThat(updatedBeaconUse.getActivity(), is(Activity.SAILING));
        assertTrue(updatedBeaconUse.getMainUse());
        assertThat(updatedBeaconUse.getMaxCapacity(), is(10));
        assertFalse(updatedBeaconUse.getVhfRadio());
        assertThat(updatedBeaconUse.getVesselName(), is("Andy"));
    }
}
