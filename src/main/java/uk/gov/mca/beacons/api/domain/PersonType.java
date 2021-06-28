package uk.gov.mca.beacons.api.domain;

import uk.gov.mca.beacons.api.jpa.entities.Person;

/**
 * An enumeration of the person types used by {@link Person}
 */
public enum PersonType {
  OWNER,
  EMERGENCY_CONTACT,
}
