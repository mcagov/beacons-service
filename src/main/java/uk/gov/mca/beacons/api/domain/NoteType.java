package uk.gov.mca.beacons.api.domain;

import uk.gov.mca.beacons.api.jpa.entities.Note;

/**
 * An enumeration of the note types used by {@link Note}
 */
public enum NoteType {
  INCIDENT,
  GENERAL,
  RECORD_HISTORY,
}
