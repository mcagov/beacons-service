package uk.gov.mca.beacons.service.registrations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.Registration;

@Service
public class RegistrationsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    RegistrationsService.class
  );

  public Registration register(Registration registration) {
    // TODO: Persist beacon, owner etc. to database, send confirmation email

    LOGGER.info("Attempting to persist registration {}", registration);

    // Create new BeaconPerson to represent owner, persist
    // For each beacon posted, create new Beacon and persist
    // -----> For each use associated with Beacon, create new BeaconUse and persist
    // For each beacon posted, create new Beacon and persist

    return registration;
  }
}
