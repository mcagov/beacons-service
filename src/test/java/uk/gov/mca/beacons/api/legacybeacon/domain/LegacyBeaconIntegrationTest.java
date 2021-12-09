package uk.gov.mca.beacons.api.legacybeacon.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.mca.beacons.api.BaseIntegrationTest;
import uk.gov.mca.beacons.api.FixtureHelper;

public class LegacyBeaconIntegrationTest extends BaseIntegrationTest {

  @Autowired
  LegacyBeaconRepository legacyBeaconRepository;

  @Autowired
  FixtureHelper fixtureHelper;

  @Test
  void shouldSaveALegacyBeacon() throws Exception {
    LegacyBeacon legacyBeacon = new LegacyBeacon();
    LegacyData legacyData = getLegacyBeaconData();
    legacyBeacon.setBeaconStatus("MIGRATED");
    legacyBeacon.setHexId(legacyData.getBeacon().getHexId());
    legacyBeacon.setOwnerEmail(legacyData.getOwner().getEmail());
    legacyBeacon.setOwnerName(legacyData.getOwner().getOwnerName());
    legacyBeacon.setUseActivities("Testing testing 123");
    legacyBeacon.setData(legacyData);

    LegacyBeacon savedLegacyBeacon = legacyBeaconRepository.save(legacyBeacon);

    assert savedLegacyBeacon.getId() != null;
  }

  private LegacyData getLegacyBeaconData() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(
      fixtureHelper.getFixture(
        "src/test/resources/fixtures/legacyBeaconData.json"
      ),
      LegacyData.class
    );
  }
}
