package uk.gov.mca.beacons.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.EnumMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Helper class to load in-memory JSON fixtures for tests.
 */
@Component
public class FixturesComponent {

  private static final String FIXTURES_BASE_PATH =
    "src/test/resources/fixtures";
  private static final String REGISTRATION_JSON_RESOURCE =
    FIXTURES_BASE_PATH + "/registrations.json";

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private Map<RegistrationsJson, Object> registrationsJson;

  @PostConstruct
  void initFixtures() {}

  public Object getRegistrationFixture(RegistrationsJson jsonFixture) {
    return registrationsJson.get(jsonFixture);
  }

  public enum RegistrationsJson {
    SINGLE_BEACON,
    MULTIPLE_BEACONS,
    NO_BEACON_TYPE,
    NO_USES,
    NO_EMERGENCY_CONTACTS,
  }
}
