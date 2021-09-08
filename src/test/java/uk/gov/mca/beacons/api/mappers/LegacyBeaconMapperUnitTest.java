package uk.gov.mca.beacons.api.mappers;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.BeaconStatus;
import uk.gov.mca.beacons.api.domain.LegacyBeacon;
import uk.gov.mca.beacons.api.utils.DateTimeParser;

@ExtendWith(MockitoExtension.class)
class LegacyBeaconMapperUnitTest {

  @InjectMocks
  private LegacyBeaconMapper legacyBeaconMapper;

  @Nested
  class ToJpaEntity {

    @Test
    void shouldSetTheHexId() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(
          Map.of(
            "hexId",
            "Hex me",
            "createdDate",
            "2020-08-01T21:33:13",
            "lastModifiedDate",
            "2021-08-01T21:33:13"
          )
        )
        .owner(emptyMap())
        .secondaryOwners(emptyList())
        .uses(emptyList())
        .emergencyContact(emptyMap())
        .build();

      final var result = legacyBeaconMapper.toJpaEntity(beacon);

      assertThat(result.getHexId(), is("Hex me"));
    }

    @Test
    void shouldSetTheOwnersEmailAddress() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(
          Map.of(
            "createdDate",
            "2020-08-01T21:33:13",
            "lastModifiedDate",
            "2021-08-01T21:33:13"
          )
        )
        .owner(Map.of("email", "beacons@mca.gov.uk"))
        .secondaryOwners(emptyList())
        .uses(emptyList())
        .emergencyContact(emptyMap())
        .build();

      final var result = legacyBeaconMapper.toJpaEntity(beacon);

      assertThat(result.getOwnerEmail(), is("beacons@mca.gov.uk"));
    }

    @Test
    void shouldSetTheCreatedDate() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(
          Map.of(
            "createdDate",
            "2020-08-01T21:33:13",
            "lastModifiedDate",
            "2021-08-01T21:33:13"
          )
        )
        .owner(emptyMap())
        .secondaryOwners(emptyList())
        .uses(emptyList())
        .emergencyContact(emptyMap())
        .build();

      final var result = legacyBeaconMapper.toJpaEntity(beacon);

      assertThat(
        result.getCreatedDate(),
        is(DateTimeParser.parse("2020-08-01T21:33:13"))
      );
    }

    @Test
    void shouldSetTheLastModifiedDate() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(
          Map.of(
            "createdDate",
            "2020-08-01T21:33:13",
            "lastModifiedDate",
            "2021-08-01T21:33:13"
          )
        )
        .owner(emptyMap())
        .secondaryOwners(emptyList())
        .uses(emptyList())
        .emergencyContact(emptyMap())
        .build();

      final var result = legacyBeaconMapper.toJpaEntity(beacon);

      assertThat(
        result.getLastModifiedDate(),
        is(DateTimeParser.parse("2021-08-01T21:33:13"))
      );
    }

    @Test
    void shouldSetTheBeaconStatus() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(
          Map.of(
            "createdDate",
            "2020-08-01T21:33:13",
            "lastModifiedDate",
            "2021-08-01T21:33:13"
          )
        )
        .owner(emptyMap())
        .beaconStatus(BeaconStatus.MIGRATED)
        .secondaryOwners(emptyList())
        .uses(emptyList())
        .emergencyContact(emptyMap())
        .build();

      final var result = legacyBeaconMapper.toJpaEntity(beacon);

      assertThat(result.getBeaconStatus(), is(BeaconStatus.MIGRATED));
    }

    @Test
    void shouldSerialiseAllFieldsIntoTheDataField() {
      final var beacon = LegacyBeacon
        .builder()
        .beacon(
          Map.of(
            "hexId",
            "Hex me",
            "createdDate",
            "2020-08-01T21:33:13",
            "lastModifiedDate",
            "2021-08-01T21:33:13"
          )
        )
        .owner(Map.of("email", "beacons@mca.gov.uk"))
        .emergencyContact(
          Map.of("details", "Mrs Beacon is my emergency contact")
        )
        .uses(singletonList(Map.of("maxPersons", 10)))
        .secondaryOwners(
          singletonList(Map.of("email", "mrsbeacon@beacons.gov.uk"))
        )
        .beaconStatus(BeaconStatus.MIGRATED)
        .build();

      final var result = legacyBeaconMapper.toJpaEntity(beacon);
      final var data = result.getData();
      final var beaconData = (Map) data.get("beacon");
      final var ownerData = (Map) data.get("owner");
      final var emergencyContactData = (Map) data.get("emergencyContact");
      final var usesData = (List<Map<String, Object>>) data.get("uses");
      final var secondaryOwnersData = (List<Map<String, Object>>) data.get(
        "secondaryOwners"
      );

      assertThat(beaconData.get("hexId"), is("Hex me"));
      assertThat(ownerData.get("email"), is("beacons@mca.gov.uk"));
      assertThat(
        emergencyContactData.get("details"),
        is("Mrs Beacon is my emergency contact")
      );
      assertThat(usesData.size(), is(1));
      assertThat(secondaryOwnersData.size(), is(1));
      assertThat(
        secondaryOwnersData.get(0).get("email"),
        is("mrsbeacon@beacons.gov.uk")
      );
    }
  }
}
