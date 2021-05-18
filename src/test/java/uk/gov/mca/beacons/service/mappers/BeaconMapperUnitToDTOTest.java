package uk.gov.mca.beacons.service.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.mca.beacons.service.linkBuilders.HateoasLinkBuilder;
import uk.gov.mca.beacons.service.linkBuilders.HateoasLinkBuilder.SupportedMethod;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconStatus;

@ExtendWith(MockitoExtension.class)
class BeaconMapperUnitToDTOTest {

  private BeaconMapper beaconMapper;

  private Beacon domainBeacon;
  private UUID beaconId;

  @Mock
  private HateoasLinkBuilder linkBuilder;

  @BeforeEach
  void init() {
    beaconMapper = new BeaconMapper(linkBuilder);
    domainBeacon = new Beacon();
    beaconId = UUID.randomUUID();
    domainBeacon.setId(beaconId);
  }

  @Test
  void shouldSetAllTheFieldsOnTheBeaconDTOFromTheDomain() {
    domainBeacon.setHexId("1");
    domainBeacon.setManufacturer("Trousers");
    domainBeacon.setModel("ASOS");
    domainBeacon.setChkCode("2");
    domainBeacon.setManufacturerSerialNumber("3");
    domainBeacon.setBeaconStatus(BeaconStatus.NEW);
    domainBeacon.setCreatedDate(LocalDateTime.of(2020, 2, 1, 0, 0, 0));
    domainBeacon.setBatteryExpiryDate(LocalDate.of(2022, 2, 1));
    domainBeacon.setLastServicedDate(LocalDate.of(2019, 2, 1));

    var beaconDTO = beaconMapper.toDTO(domainBeacon);
    var dtoAttributes = beaconDTO.getAttributes();
    assertThat(beaconDTO.getId(), is(beaconId));
    assertThat(dtoAttributes.get("hexId"), is("1"));
    assertThat(dtoAttributes.get("manufacturer"), is("Trousers"));
    assertThat(dtoAttributes.get("model"), is("ASOS"));
    assertThat(dtoAttributes.get("chkCode"), is("2"));
    assertThat(dtoAttributes.get("manufacturerSerialNumber"), is("3"));
    assertThat(dtoAttributes.get("status"), is(BeaconStatus.NEW));
    assertThat(
      dtoAttributes.get("createdDate"),
      is(LocalDateTime.of(2020, 2, 1, 0, 0, 0))
    );
    assertThat(
      dtoAttributes.get("batteryExpiryDate"),
      is(LocalDate.of(2022, 2, 1))
    );
    assertThat(
      dtoAttributes.get("lastServicedDate"),
      is(LocalDate.of(2019, 2, 1))
    );
    then(linkBuilder)
      .should(times(1))
      .addLinkFor(domainBeacon, SupportedMethod.GET, beaconDTO);
    then(linkBuilder)
      .should(times(1))
      .addLinkFor(domainBeacon, SupportedMethod.PATCH, beaconDTO);
  }
}
