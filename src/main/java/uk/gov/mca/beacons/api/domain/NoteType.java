package uk.gov.mca.beacons.api.domain;

import uk.gov.mca.beacons.api.jpa.entities.BeaconNote;

/**
 * An enumeration of the note types used by {@link BeaconNote}
 */
public enum NoteType {
  INCIDENT,
  GENERAL,
  RECORD_HISTORY,
}
